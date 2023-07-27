package trackour.trackour.views.playlist;

import java.util.List;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import se.michaelthelin.spotify.model_objects.IPlaylistItem;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import trackour.trackour.model.CustomUserDetailsService;
import trackour.trackour.security.SecurityViewService;
import se.michaelthelin.spotify.model_objects.specification.Track;
import trackour.trackour.spotify.PlaylistsItems;
import trackour.trackour.views.components.NavBar;

@Route("PlaylistItems/:playlistItemId/:playlistName")
@PageTitle("Playlists")
@AnonymousAllowed
public class PlaylistItemsView extends Div implements BeforeEnterObserver {

    VerticalLayout contentContainer;
    private String playlistItemId;
    private String playlistName;

    public PlaylistItemsView(SecurityViewService securityViewService,
            CustomUserDetailsService customUserDetailsService) {
        NavBar navbar = new NavBar(customUserDetailsService, securityViewService);
        contentContainer = new VerticalLayout();
        navbar.setContent(contentContainer);
        add(navbar);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        // setText(String.format("Hello, %s!", parameter));
        playlistItemId = event.getRouteParameters().get("playlistItemId").get();
        playlistName = event.getRouteParameters().get("playlistName").get();
        PlaylistsItems items = new PlaylistsItems(playlistItemId);
        List<PlaylistTrack> playlistItem = items.getItemsInPlaylist();
        H1 header = new H1(new Text("Enjoy, " + playlistName + " !!"));
        contentContainer.add(header);
        VerticalLayout trackView = new VerticalLayout();

        for (PlaylistTrack playlistTrack : playlistItem) {
            IPlaylistItem track = playlistTrack.getTrack();
            String trackName = track.getName();
            // String trackURL = track.getHref();

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

            Div trackNameDiv = new Div(new H3(new Text(trackName)));
            trackNameDiv.getStyle().set("margin-left", "10px");
            trackNameDiv.getStyle().setWidth("100%");
            trackNameDiv.add(visuals);

            trackLayout.add(trackNameDiv);

            HorizontalLayout trackList = new HorizontalLayout();
            trackList.add(trackLayout);
            trackView.add(trackList);
        }
        contentContainer.add(trackView);
    }
}