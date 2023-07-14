package trackour.trackour.views.forgotPassword;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.router.BeforeLeaveObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import trackour.trackour.models.CustomUserDetailsService;
//import trackour.trackour.models.PasswordToken;
//import trackour.trackour.models.PasswordTokenService;
import trackour.trackour.models.User;
import trackour.trackour.security.SecurityService;
import trackour.trackour.security.SecurityViewHandler;
import trackour.trackour.views.login.LoginPage;

@Route("resetPassword")
@PageTitle("Reset Password")
@AnonymousAllowed
public class enterEmailView extends VerticalLayout implements BeforeLeaveObserver, BeforeEnterObserver {

    SecurityViewHandler securityViewHandler;
    CustomUserDetailsService customUserDetailsService;
    //PasswordTokenService passwordTokenService;

    public enterEmailView(SecurityViewHandler securityViewHandler, SecurityService securityService, CustomUserDetailsService customUserDetailsService) {

        this.securityViewHandler = securityViewHandler;
        this.customUserDetailsService = customUserDetailsService;
        //this.passwordTokenService = passwordTokenService;

        H3 title = new H3("Enter your email");

        Span text = new Span("An email with a link to reset your password will be sent shortly. Remember to check your spam folder!");

        EmailField emailField = new EmailField("Email");
        
        Span error = new Span("");

        Button submit = new Button("Submit", e -> getEmail(emailField.getValue(), error));
        submit.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        RouterLink loginLink = new RouterLink("return to login page", LoginPage.class);

        VerticalLayout layout = new VerticalLayout();
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setMargin(true);
        layout.setMaxWidth("500px");

        setAlignItems(FlexComponent.Alignment.CENTER);

        FormLayout form = new FormLayout();
        form.add(emailField, submit);
        form.setColspan(emailField,1);
        form.setColspan(submit,1);

        layout.add(title, text, form, error, loginLink);

        add(layout);
    }

    public void getEmail(String email, Span error) {
        User user;

        System.out.println("Searching for user...");

        if (customUserDetailsService.getByEmail(email).isPresent()) {
            user = customUserDetailsService.getByEmail(email).get();
            System.out.println("User found: " + user.getUsername());
            error.setText("The password reset email has been sent!");

            user.generatePasswordResetToken();
            System.out.println("Password token: " + user.getPasswordResetToken());
            customUserDetailsService.update(user);

            //PasswordToken token = new PasswordToken();
            //passwordTokenService.newToken(token);

        } else {
            System.out.println("Could not find user");
            error.setText("Email not found! Please try a different email.");
        }
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        // this method call reroutes get requests to this view if the current session is already authenticated
        this.securityViewHandler.handleAnonymousOnly(beforeEnterEvent, true);
        if (beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
        }
    }

    @Override
    public void beforeLeave(BeforeLeaveEvent event) {
        // reroute to error page
        if (event.hasUnknownReroute()){
            System.out.println("Rerouting to Error Page!");
        }
    }
    
}
