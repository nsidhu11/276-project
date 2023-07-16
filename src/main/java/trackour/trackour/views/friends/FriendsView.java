package trackour.trackour.views.friends;
//import java.util.Arrays;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;

import jakarta.annotation.security.RolesAllowed;

import trackour.trackour.model.CustomUserDetailsService;
import trackour.trackour.model.User;
import trackour.trackour.security.SecurityService;
import trackour.trackour.security.SecurityViewHandler;
import trackour.trackour.views.home.NavBar;

@Route("friends")
@PageTitle("Friends")
@RolesAllowed({"ADMIN", "USER"})
public class FriendsView extends VerticalLayout{
    SecurityViewHandler securityViewHandler;
    SecurityService securityService;
    CustomUserDetailsService customUserDetailsService;

    Grid<User> currentFriendsGrid = new Grid<>(User.class, false);
    Grid<User> friendRequestGrid = new Grid<>(User.class, false);

    public FriendsView(SecurityViewHandler securityViewHandler, SecurityService securityService,
        CustomUserDetailsService customUserDetailsService) {

            this.securityViewHandler = securityViewHandler;
            this.securityService = securityService;
            this.customUserDetailsService = customUserDetailsService;

            NavBar navigation = new NavBar(securityService, customUserDetailsService, securityViewHandler);

            H3 friendRequestTitle = new H3("Add a new friend");
            TextField friendRequestInput = new TextField("Enter Username");
            Button friendRequestButton = new Button("Submit");
            Span confirmationText = new Span("");

            H3 pendingFriendRequest = new H3("Incoming friend requests");

            H3 currentFriendsTitle = new H3("Your current friends");

            configureRequestGrid();
            configureFriendsGrid();

            addClassName("friends-view");
            setSizeFull();

            HorizontalLayout layout = new HorizontalLayout();

            VerticalLayout friendRequestLayout = new VerticalLayout();
            VerticalLayout currentFriendsLayout = new VerticalLayout();

            FormLayout friendRequestForm = new FormLayout();

            friendRequestForm.setSizeFull();
            friendRequestForm.add(friendRequestInput, friendRequestButton);

            friendRequestLayout.setSizeFull();
            friendRequestLayout.add(friendRequestTitle, friendRequestForm, confirmationText,
                                        pendingFriendRequest, friendRequestGrid);

            currentFriendsLayout.setSizeFull();
            currentFriendsLayout.add(currentFriendsTitle, currentFriendsGrid);


            layout.setSizeFull();
            layout.add(friendRequestLayout, currentFriendsLayout);

            add(navigation.generateNavBar(), layout);
            

            User currentUser = customUserDetailsService.getByUsername(securityService.getAuthenticatedUser().getUsername()).get();
            /*
            List<Long> friends = new ArrayList<Long>();
            
            friends.add((long) 100);
            friends.add((long) 200);
            friends.add((long) 300);

            currentUser.setFriendRequests(friends);

            customUserDetailsService.update(currentUser);

            currentUser = customUserDetailsService.getByUsername(securityService.getAuthenticatedUser().getUsername()).get();
            
            System.out.println(Arrays.toString(currentUser.getFriendRequests().toArray()));
            */
    }

    private void configureRequestGrid() {
        this.friendRequestGrid.addClassNames("friends-grid");
        this.friendRequestGrid.setSizeFull();
        this.friendRequestGrid.addColumn("username");
        this.friendRequestGrid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private void configureFriendsGrid() {
        this.currentFriendsGrid.addClassNames("friends-grid");
        this.currentFriendsGrid.setSizeFull();
        this.currentFriendsGrid.setColumns("username");
        this.currentFriendsGrid.getColumns().forEach(col -> col.setAutoWidth(true));
    }
}
