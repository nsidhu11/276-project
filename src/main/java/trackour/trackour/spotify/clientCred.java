package trackour.trackour.spotify;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import trackour.trackour.views.api.ClientKeys;

import org.apache.hc.core5.http.ParseException;
// import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties.Web.Client;

import java.io.IOException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

// import io.github.cdimascio.dotenv.Dotenv;

public class clientCred {
    // private static final Dotenv dotenv = Dotenv.configure().load();
    private static final String clientId = ClientKeys.CLIENT_ID.getKey();
    private static final String clientSecret = ClientKeys.CLIENT_SECRET.getKey();

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