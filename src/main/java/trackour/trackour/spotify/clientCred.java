package trackour.trackour.spotify;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRefreshRequest;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import trackour.trackour.views.api.ClientKeys;

import org.apache.hc.core5.http.ParseException;
// import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties.Web.Client;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

// import io.github.cdimascio.dotenv.Dotenv;

public class clientCred {
    // private static final Dotenv dotenv = Dotenv.configure().load();
    private static final String clientId = ClientKeys.CLIENT_ID.getKey();
    private static final String clientSecret = ClientKeys.CLIENT_SECRET.getKey();
    private static Integer accessTokenExpiresIn = null;
    private static final long TOKEN_LIFE_SPAN_MILLISECONDS = 3600 * 1000L;

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .build();

    private static final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
            .build();

    public static void clientCredentials_Sync() {
        try {
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();

            // Set access token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());
            // set the access tiken expiry time
            accessTokenExpiresIn = clientCredentials.getExpiresIn();

            System.out.println(
                    "Expires in: " + clientCredentials.getExpiresIn() + " " + clientCredentials.getAccessToken());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void clientCredentials_Async() {
        try {
            final CompletableFuture<ClientCredentials> clientCredentialsFuture = clientCredentialsRequest
                    .executeAsync();

            // Thread free to do other tasks...

            // Example Only. Never block in production code.
            final ClientCredentials clientCredentials = clientCredentialsFuture.join();

            // Set access token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());
            
            // set the access tiken expiry time
            accessTokenExpiresIn = clientCredentials.getExpiresIn();

            // System.out.println("Expires in: " + clientCredentials.getExpiresIn() +
            // clientCredentials.getAccessToken());
        } catch (CompletionException e) {
            System.out.println("Error: " + e.getCause().getMessage());
        } catch (CancellationException e) {
            System.out.println("Async operation cancelled.");
        }
    }

    public static String getAccessToken() {
        clientCredentials_Async(); // Call the synchronous method to obtain the access token
        // System.out.println(spotifyApi.getAccessToken());
        return spotifyApi.getAccessToken();
    }

    // static method to refresh the access token if it is expired
    @Scheduled(fixedRate = TOKEN_LIFE_SPAN_MILLISECONDS)
    public static void refreshAccessToken() {
        // Get the current access token and its expiration date from the SpotifyApi instance
        String accessToken = spotifyApi.getAccessToken();
        Integer accessTokenExpirationSeconds = accessTokenExpiresIn;
        Long accessTokenExpirationMilliseconds = accessTokenExpirationSeconds * 1000L;
        Date accessTokenExpirationDate = new Date(accessTokenExpirationMilliseconds);
    
        // if the access token is null or is expired, then refresh it
        if (accessToken == null || accessTokenExpirationDate == null || accessTokenExpirationDate.before(new Date())) {
        // Create a request object for refreshing the access token
        AuthorizationCodeRefreshRequest authorizationCodeRefreshRequest = spotifyApi.authorizationCodeRefresh()
            .build();
    
        try {
            // Execute the request and get the response
            AuthorizationCodeCredentials clientCredentials = authorizationCodeRefreshRequest.execute();
    
            // Set the refreshed access token and expiration dates on the SpotifyApi instance
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());
            accessTokenExpiresIn = clientCredentials.getExpiresIn();
    
            // Print the new access token
            System.out.println("Refreshed access token: " + spotifyApi.getAccessToken());
            System.out.println("Expires in: " + accessTokenExpiresIn);
    
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
        } else {
        // else do nothing
        System.out.println("Access token is still valid.");
        }
    }

    // public static String getAuthorizationHeader(){
    // }

    // public static void   (String[] args) {
    //     // clientCredentials_Sync();
    //     // clientCredentials_Async();

    //     String accessToken = getAccessToken();
    //     if (accessToken != null) {
    //         System.out.println("Access Token: " + accessToken);
    //     }
    // }
}