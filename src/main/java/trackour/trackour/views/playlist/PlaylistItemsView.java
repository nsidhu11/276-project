package trackour.trackour.views.playlist;

import java.util.List;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.component.html.HtmlObject;

import se.michaelthelin.spotify.model_objects.IPlaylistItem;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.model_objects.specification.Track;
import trackour.trackour.spotify.PlaylistsItems;

@Route(value = "PlaylistItems")
@PageTitle("Playlists")
@AnonymousAllowed
public class PlaylistItemsView extends Div implements HasUrlParameter<String> {
    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        // setText(String.format("Hello, %s!", parameter));
        PlaylistsItems items = new PlaylistsItems(parameter);
        List<PlaylistTrack> playlistItem = items.getItemsInPlaylist();

        VerticalLayout trackView = new VerticalLayout();

        for (PlaylistTrack playlistTrack : playlistItem) {
            IPlaylistItem track = playlistTrack.getTrack();
            String trackName = track.getName();
            String trackURL = track.getHref();

            Image trackImage = null;
            if (track instanceof Track) {
                Track trackItem = (Track) track;
                trackImage = new Image(trackItem.getAlbum().getImages()[0].getUrl(), "Track Image");
                trackImage.setWidth("50px");
                trackImage.setHeight("50px");
            }

            Hr visuals = new Hr();
            visuals.getStyle().setWidth("100%");
            visuals.getStyle().set("margin", "0");

            FlexLayout trackLayout = new FlexLayout();
            trackLayout.getStyle().set("align-items", "center");

            if (trackImage != null) {
                trackLayout.add(trackImage);
            }

            Div trackNameDiv = new Div(new H2(new Text(trackName)));
            trackNameDiv.getStyle().set("margin-left", "10px");
            trackNameDiv.getStyle().setWidth("100%");
            trackNameDiv.add(visuals);

            trackLayout.add(trackNameDiv);

            HorizontalLayout trackList = new HorizontalLayout();
            trackList.add(trackLayout);
            trackView.add(trackList);
        }
        add(trackView);
        // HtmlObject audioPlayer = new HtmlObject("audio");
        // audioPlayer.getElement().setAttribute("autoplay", true);
        // audioPlayer.getElement().setAttribute("controls", true);

        // // Get the URL of the track you want to play
        // String trackUrl = "https://example.com/path/to/your/track.mp3";
        // audioPlayer.getElement().setAttribute("src", trackUrl);

        // // Add the audio player to your layout
        // add(trackView, audioPlayer);
    }
}