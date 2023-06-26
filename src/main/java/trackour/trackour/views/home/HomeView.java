package trackour.trackour.views.home;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;
import trackour.trackour.models.CustomUserDetailsService;
import trackour.trackour.security.SecurityService;

@Route("")
@PermitAll
public class HomeView extends VerticalLayout {
    public HomeView(SecurityService securityService, CustomUserDetailsService customUserDetailsService) {
        H1 header = new H1("Trackour");
        
        String sessionUsername = securityService.getAuthenticatedUser().getUsername();
        // since logged in, no need to verify if this optional is empty
        String displayNameString = customUserDetailsService.getByUsername(sessionUsername).get().getDisplayName();
        Text displayNameTxt = new Text("@" + displayNameString);
        Button signUpButton = new Button("Sign Up");
        signUpButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        signUpButton.addClassName("button-hover-effect");
        signUpButton.addClickListener(event -> {
            UI.getCurrent().navigate("signUp");
        });

        Button LoginButton = new Button("Login");
        LoginButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        LoginButton.addClassName("button-hover-effect");
        LoginButton.addClickListener(event -> {
            UI.getCurrent().navigate("login");
        });

        ComboBox<String> languageComboBox = new ComboBox<>();
        languageComboBox.setPlaceholder("Music language");
        languageComboBox.setItems("English", "Punjabi", "Spanish", "French", "German", "Hindi");

        TextField searchField = new TextField();
        searchField.setPlaceholder("Search Any Music");

        HorizontalLayout topNavButtons = new HorizontalLayout(displayNameTxt, LoginButton);
        topNavButtons.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        topNavButtons.getStyle().set("gap", "10px"); // Add spacing between the buttons

        // Create a layout for the header and buttons
        HorizontalLayout topNavBar = new HorizontalLayout(header, searchField, languageComboBox, topNavButtons);
        topNavBar.setAlignItems(FlexComponent.Alignment.CENTER);
        topNavBar.setWidthFull();
        topNavBar.expand(header);
        topNavBar.expand(searchField);
        topNavBar.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        add(topNavBar);
    }
}
