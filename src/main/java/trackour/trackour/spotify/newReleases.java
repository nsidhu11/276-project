package trackour.trackour.spotify;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.requests.data.browse.GetListOfNewReleasesRequest;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;

import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class NewReleases {
    private final static ClientCred clientCred = new ClientCred();
    private static final String accessToken = clientCred.getAccessToken();
    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setAccessToken(accessToken)
            .build();
    private static final GetListOfNewReleasesRequest getListOfNewReleasesRequest = spotifyApi.getListOfNewReleases()
            // .country(CountryCode.SE)
            // .limit(10)
            // .offset(0)
            .build();

    public static void getListOfNewReleases_Sync() {
        try {
            final Paging<AlbumSimplified> albumSimplifiedPaging = getListOfNewReleasesRequest.execute();

            System.out.println("Total: " + albumSimplifiedPaging.getTotal());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void getListOfNewReleases_Async() {
        try {
            final CompletableFuture<Paging<AlbumSimplified>> pagingFuture = getListOfNewReleasesRequest.executeAsync();
            final Paging<AlbumSimplified> albumSimplifiedPaging = pagingFuture.join();
            AlbumSimplified[] albumsArray = albumSimplifiedPaging.getItems();
            List<AlbumSimplified> albums = Arrays.asList(albumsArray);

            System.out.println("Total: " + albumSimplifiedPaging.getTotal());
            for (AlbumSimplified album : albums) {
                System.out.println("Album Name: " + album.getName());
            }
        } catch (CompletionException e) {
            System.out.println("Error: " + e.getCause().getMessage());
        } catch (CancellationException e) {
            System.out.println("Async operation cancelled.");
        }
    }

    public static List<AlbumSimplified> getNewReleases() {

        final CompletableFuture<Paging<AlbumSimplified>> pagingFuture = getListOfNewReleasesRequest.executeAsync();
        final Paging<AlbumSimplified> albumSimplifiedPaging = pagingFuture.join();

        return Arrays.asList(albumSimplifiedPaging.getItems());
    }

    // public static void main(String[] args) {
    // getNewReleases();
    // }
}
