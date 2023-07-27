package trackour.trackour.views.playlist;

import java.util.List;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import se.michaelthelin.spotify.model_objects.IPlaylistItem;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.model_objects.specification.Track;
import trackour.trackour.model.CustomUserDetailsService;
import trackour.trackour.security.SecurityViewService;
import trackour.trackour.spotify.PlaylistsItems;
import trackour.trackour.views.components.NavBar;

@Route(value = "PlaylistItems")
@RouteAlias("playlistitems")
@PageTitle("Playlist Items | Trackour")
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

        System.out.println("param: " + parameter);
        // String catId = parameter;
        // Explore catExplore = new Explore();
        // if (catId == null) {
        //     UI.getCurrent().getPage().setTitle("Search Results | Trackour");
        // }
        // else {
        //     catExplore.getCategories().forEach(cat -> {
        //         if (cat.getId() != null && cat.getId().equals(catId)) {
        //             if (cat.getName() != null){
        //                 UI.getCurrent().getPage().setTitle("Trackour - Playlist View - " + cat.getName());
        //             }
        //             else {
        //                 UI.getCurrent().getPage().setTitle("Playlist View | Trackour");
        //             }
        //         }
        //     });
        // }
        // setText(String.format("Hello, %s!", parameter));
        PlaylistsItems items = new PlaylistsItems(parameter);
        List<PlaylistTrack> playlistItem = items.getItemsInPlaylist();

        VerticalLayout trackView = new VerticalLayout();

        if (playlistItem != null && !playlistItem.isEmpty()) {
            for (PlaylistTrack playlistTrack : playlistItem) {
                IPlaylistItem track = playlistTrack.getTrack();
                String trackName = track.getName();
                // String trackURL = track.getHref();
        
                Image trackImage = null;
                if (track instanceof Track) {
                    Track trackItem = (Track) track;
                    // check for possible null values before accessing the array element
                    if (trackItem.getAlbum() != null && trackItem.getAlbum().getImages() != null && trackItem.getAlbum().getImages().length > 0 && trackItem.getAlbum().getImages()[0].getUrl() != null) {
                        trackImage = new Image(trackItem.getAlbum().getImages()[0].getUrl(), "Track Image");
                        trackImage.setWidth("50px");
                        trackImage.setHeight("50px");
                    }
                    else {
                        // handle the case when there is no image URL
                    }
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
        }
        else {
            // use a common method to display an empty message
            displayEmptyMessage(trackView);
        }

        contentContainer.add(trackView);
    }

    // a common method to display an empty message
    private void displayEmptyMessage(VerticalLayout layout) {
        Div emptyListmessageDiv = new Div(new H3(new Text("This playlist is empty at the moment.")));
        emptyListmessageDiv.getStyle().set("color", "grey");
        emptyListmessageDiv.getStyle().set("text-align", "center");
        layout.add(emptyListmessageDiv);
    }
}
