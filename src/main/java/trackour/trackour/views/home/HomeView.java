package trackour.trackour.views.home;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;
import trackour.trackour.models.CustomUserDetailsService;
import trackour.trackour.security.SecurityViewHandler;

@Route("")
// Admins are users but also have the "admin" special role so pages that can be
// viewed by
// both users and admins should have the admin role specified as well
@RolesAllowed({ "ADMIN", "USER" })
public class HomeView extends VerticalLayout {
    MenuBar mobileVMenuBar;
    Component mobileView;

    public HomeView(SecurityViewHandler securityViewHandler,
            CustomUserDetailsService customUserDetailsService) {
        H1 header = new H1("Trackour");

        Optional<UserDetails> username = securityViewHandler.getRequestSession();
        String sessionUsername = username.get().getUsername();
        // String sessionUsername =
        // securityService.getAuthenticatedUser().getUsername();
        // since logged in, no need to verify if this optional is empty
        String displayNameString = customUserDetailsService.getByUsername(sessionUsername).get().getDisplayName();
        Text displayNameTxt = new Text("@" + displayNameString);
        Button signUpButton = new Button("Sign Up");
        signUpButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        signUpButton.addClassName("button-hover-effect");
        signUpButton.addClickListener(event -> {
            UI.getCurrent().navigate("signUp");
        });

        Button LoginButton = new Button("Logout");
        LoginButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        LoginButton.addClassName("button-hover-effect");
        LoginButton.addClickListener(event -> {
            securityViewHandler.logOut();
        });

        ComboBox<String> languageComboBox = new ComboBox<>();
        languageComboBox.setPlaceholder("Music language");
        languageComboBox.setItems("English", "Punjabi", "Spanish", "French", "German", "Hindi");

        TextField searchField = new TextField();
        searchField.setPlaceholder("Search Music");
        searchField.setPrefixComponent(new Icon("lumo", "search"));

        Button mediaShelfButton = new Button("Media Shelf", new Icon(VaadinIcon.MUSIC));
        Button exploreButton = new Button("Explore");

        HorizontalLayout topNavButtons = new HorizontalLayout(exploreButton, searchField, languageComboBox,
                mediaShelfButton, displayNameTxt, LoginButton);
        topNavButtons.addClassName("topNavButtons");
        topNavButtons.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        topNavButtons.setWidthFull();

        HorizontalLayout topNavBar = new HorizontalLayout(header, topNavButtons);

        topNavBar.setAlignItems(FlexComponent.Alignment.CENTER);
        topNavBar.setWidthFull();
        topNavBar.expand(searchField);
        topNavBar.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        add(topNavBar);

        Page page = UI.getCurrent().getPage();
        page.addBrowserWindowResizeListener(
                event -> {
                    int windowWidth = event.getWidth();
                    if (windowWidth <= 1000) {
                        // Display the mobile menu

                        mobileVMenuBar = new MenuBar();
                        MenuItem item = mobileVMenuBar.addItem("Menu"); // ,new Icon("lumo", "menu")
                        SubMenu subMenu = item.getSubMenu();
                        subMenu.addItem(exploreButton);
                        subMenu.addItem(searchField);
                        subMenu.addItem(languageComboBox);
                        subMenu.addItem(mediaShelfButton);
                        subMenu.addItem(displayNameTxt);
                        subMenu.addItem(LoginButton);

                        mobileVMenuBar.addClassName("mobileVMenu");
                        mobileView = new HorizontalLayout(mobileVMenuBar);
                        add(header, mobileView);

                    }
                    if (windowWidth > 1000 && mobileView != null) {
                        // Remove the mobile menu if it was previously added
                        // remove(mobileView);
                        // add(topNavBar);
                        // mobileVMenuBar = null;
                        SubMenu subMenu = mobileVMenuBar.getItems().get(0).getSubMenu();
                        subMenu.removeAll();
                        mobileVMenuBar.removeAll();
                        remove(mobileView);
                        add(topNavButtons);
                        // mobileVMenuBar = null;
                        // mobileView = null;
                    }
                });
    }
}