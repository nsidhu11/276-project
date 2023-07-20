package trackour.trackour.spotify;

import java.util.Arrays;
import java.util.List;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;
import org.apache.hc.core5.http.ParseException;
// import trackour.trackour.views.home.HomeView;
import trackour.trackour.views.home.NavBar;

import java.io.IOException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class SearchTrack {
  
  private final static ClientCred clientCred = new ClientCred();
  private static final String accessToken = clientCred.getAccessToken();// "taHZ2SdB-bPA3FsK3D7ZN5npZS47cMy-IEySVEGttOhXmqaVAIo0ESvTCLjLBifhHOHOIuhFUKPW1WMDP7w6dj3MAZdWT8CLI2MkZaXbYLTeoDvXesf2eeiLYPBGdx8tIwQJKgV8XdnzH_DONk";
  static String q = NavBar.getSearchValue();

  private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
    .setAccessToken(accessToken)
    .build();
  private static SearchTracksRequest searchTracksRequest = spotifyApi.searchTracks(q)
//          .market(CountryCode.SE)
//          .limit(10)
//          .offset(0)
//          .includeExternal("audio")
    .build();

  public static void searchTracks_Sync() {
      
    try {
      final Paging<Track> trackPaging = searchTracksRequest.execute();
      System.out.println(searchTracksRequest.execute().getItems());

      System.out.println("Total: " + trackPaging.getTotal());
    } catch (IOException | SpotifyWebApiException | ParseException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  public static void searchTracks_Async() {
    try {
      final CompletableFuture<Paging<Track>> pagingFuture = searchTracksRequest.executeAsync();

      // Thread free to do other tasks...

      // Example Only. Never block in production code.
      final Paging<Track> trackPaging = pagingFuture.join();

      System.out.println("Total: " + trackPaging.getTotal());
    } catch (CompletionException e) {
      System.out.println("Error: " + e.getCause().getMessage());
    } catch (CancellationException e) {
      System.out.println("Async operation cancelled.");
    }
  }

 public static List<Track> getTrack() {

    SearchTracksRequest tracksRequest = spotifyApi.searchTracks(NavBar.getSearchValue()).build();

    List<Track> songs= null;

    try {
      final Paging<Track> trackPaging = tracksRequest.execute();
      songs = Arrays.asList(trackPaging.getItems());

      System.out.println("Total: " + trackPaging.getTotal());
    } catch (IOException | SpotifyWebApiException | ParseException e) {
      System.out.println("Error: " + e.getMessage());
    }
        return songs;
    }
}
