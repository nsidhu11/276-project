package trackour.trackour.views.signup;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("signUp")
@PageTitle("Sign Up")
@AnonymousAllowed

// public class User {
// private String username;
// private String password;
// // Other user properties and getters/setters
// }

public class SignUpView extends VerticalLayout {
    public SignUpView() {

        TextField usernameField = new TextField("Username");
        PasswordField passwordField = new PasswordField("Password");
        Button signUpButton = new Button("Sign Up");
        signUpButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // Add a click listener to the sign-up button
        signUpButton.addClickListener(event -> {

            String username = usernameField.getValue();
            String password = passwordField.getValue();

            // Validate inputs
            if (!isValidUsername(username)) {
                // Display an error message for invalid username
                Notification.show("Invalid username", 3000, Notification.Position.MIDDLE);
                return;
            }

            if (!isValidPassword(password)) {
                // Display an error message for invalid password
                Notification.show("Invalid password", 3000, Notification.Position.MIDDLE);
                return;
            }

            // Perform the sign-up logic, e.g., add user to database,

            // Create a new user object
            // User newUser = new User();
            // newUser.setUsername(username);
            // newUser.setPassword(password);

            // // Add the user to the list (in-memory "database")
            // userList.add(newUser);

            // Redirect or navigate to a success page or login page
            UI.getCurrent().navigate("");
        });

        // Add form components to the layout
        add(usernameField, passwordField, signUpButton);
    }

    private boolean isValidUsername(String username) {
        // Perform validation logic and return true or false based on the result
        // e.g., check if the username meets the required criteria
        return !username.isEmpty() && username.length() >= 3;
    }

    private boolean isValidPassword(String password) {
        // Perform validation logic and return true or false based on the result
        // e.g., check if the password meets the required complexity rules
        return password.length() >= 8;
    }

}
