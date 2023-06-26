package trackour.trackour.views.signup;
import trackour.trackour.models.CustomUserDetailsService;
import trackour.trackour.security.SecurityViewHandler;
import trackour.trackour.views.login.LoginPage;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.router.BeforeLeaveObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("signup")
@PageTitle("SignUp")
@AnonymousAllowed
public class SignUpView extends VerticalLayout implements BeforeEnterObserver, BeforeLeaveObserver {
    
    @Autowired
    SecurityViewHandler securityViewHandler;

    @Autowired
    CustomUserDetailsService userService;
    
    private LoginForm signupForm;

    private LoginI18n signupI18n;

    LoginI18n.ErrorMessage i18nErrorMessage;
    
    public SignUpView(SecurityViewHandler securityViewHandler,
    
     CustomUserDetailsService userService) {
        
        // initialize variables
        this.securityViewHandler = securityViewHandler;
        this.userService = userService;

        signupForm = new LoginForm();

        // customize form
        signupI18n = LoginI18n.createDefault();

        final String validationAdditionalInfo = "Username must be >=3 characters & Password must be >=8 characters";
        final String signupErrorMessage = "Check that Username and Password are valid and that Username has not already been registered then try again.";
        // hint at bottom of login form to clarify what's valid 
        signupI18n.setAdditionalInformation(validationAdditionalInfo);

        LoginI18n.Form signupI18nForm = signupI18n.getForm();
        signupI18nForm.setTitle("Signup");
        signupI18nForm.setUsername("Username");
        signupI18nForm.setPassword("Password");
        signupI18nForm.setSubmit("Sign up");
        signupI18nForm.setForgotPassword(null);
        signupI18n.setForm(signupI18nForm);

        // default error message
        i18nErrorMessage = signupI18n.getErrorMessage();
            i18nErrorMessage.setTitle("Wrong Username or Password.");
            i18nErrorMessage.setMessage(
                    signupErrorMessage);
            signupI18n.setErrorMessage(i18nErrorMessage);
                
        signupForm.setI18n(signupI18n);

        // more customization
        addClassName("signup-view");
        setSizeFull();

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        signupForm.addLoginListener(event -> {
            System.out.println("Attempt Signup !!!");
            String username = event.getUsername();
            String password = event.getPassword();

            // Validate inputs
            /**
             * navigating to that error endpoint.
             * the "error" keyword gets caught by the "beforeEnter()" method
             * and then tells this registration form that the entered credentials
             * are invalid
             */
            if (!(isValidUsername(username) && isValidPassword(password))) {
                UI.getCurrent().navigate("signup?error#403");
                return;
            }
            boolean isExistingAlready =  !registerNewUser(username, password);
            // otherwise attempt registering new user
            if (isExistingAlready){
                System.out.println("isExistingAlready " + isExistingAlready);
                // else signup successful and proceed to landing page
                UI.getCurrent().navigate("signup?error#409");
                return;
            }

            // else register successful
            UI.getCurrent().navigate("redirect:/login");
        });

        // forgot password link since we don't have that planned out yet
        signupForm.setForgotPasswordButtonVisible(!isVisible());

        RouterLink loginLink = new RouterLink("or Login", LoginPage.class);

        add(new H1("Trackour"), signupForm, loginLink);
        // constructor end
    }

    private boolean registerNewUser(String username, String password) {
        // userDetailsService to register usr to database. Validations done by said saervice 
        return userService.registerUser(username, password);
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

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        // this call reroutes get requests to this view if the current session is already authenticated
        this.securityViewHandler.handleAnonymousOnly(event, true, "/");
        System.out.println("Before entering login page!");
        Map<String, List<String>> errorLink = event.getLocation().getQueryParameters().getParameters();
        if (errorLink.containsKey("error")){
            signupForm.setError(true);
        }
    }

    @Override
    public void beforeLeave(BeforeLeaveEvent event) {
        // reroute to error page
        if (event.hasUnknownReroute()){
            System.out.println("Rerouting to Error Page!");
            // event.forwardTo("error");
        }
    }
}
