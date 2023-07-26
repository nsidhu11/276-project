package trackour.trackour.views.playlist;

import java.util.List;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import se.michaelthelin.spotify.model_objects.IPlaylistItem;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import trackour.trackour.model.CustomUserDetailsService;
import trackour.trackour.security.SecurityViewService;
import trackour.trackour.spotify.PlaylistsItems;
import trackour.trackour.views.components.NavBar;

@Route(value = "PlaylistItems")
@PageTitle("Playlists")
@AnonymousAllowed
public class PlaylistItemsView extends Div implements HasUrlParameter<String> {

    VerticalLayout contentContainer;
    public PlaylistItemsView(SecurityViewService securityViewService,
    CustomUserDetailsService customUserDetailsService) {
        NavBar navbar = new NavBar(customUserDetailsService, securityViewService);
        contentContainer = new VerticalLayout();
        navbar.setContent(contentContainer);
        add(navbar);
    }
    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        // setText(String.format("Hello, %s!", parameter));
        PlaylistsItems items = new PlaylistsItems(parameter);
        List<PlaylistTrack> playlistItem = items.getItemsInPlaylist();

        VerticalLayout trackView = new VerticalLayout();

        for (PlaylistTrack playlistTrack : playlistItem) {
            String trackName = playlistTrack.getTrack().getName();
            System.out.println("Track Name: " + trackName);
            Div tracks = new Div(new H2(new Text(trackName)));
            Hr visuals = new Hr();
            visuals.getStyle().setWidth("100%");
            visuals.getStyle().set("margin", "0");
            tracks.add(visuals);
            // tracks.setWidth("100%");
            HorizontalLayout trackList = new HorizontalLayout();
            trackList.add(tracks);
            trackView.add(trackList);
        }
        contentContainer.add(trackView);
    }
}