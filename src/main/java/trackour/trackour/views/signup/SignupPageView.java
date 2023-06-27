package trackour.trackour.views.signup;
import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import trackour.trackour.models.CustomUserDetailsService;
import trackour.trackour.security.SecurityViewHandler;

@Route("signup")
@PageTitle("SignUp")
@AnonymousAllowed
public class SignupPageView extends VerticalLayout {

    @Autowired
    SecurityViewHandler securityViewHandler;

    @Autowired
    CustomUserDetailsService userService;
   
    public SignupPageView(SecurityViewHandler securityViewHandler, CustomUserDetailsService userService) {
        // BeanValidationBinder<UserDetails> binder = new BeanValidationBinder<>(UserDetails.class);
        // boolean enablePasswordValidation = true;
        CustomSignupForm signupForm = new CustomSignupForm(userService);

        // final String validationAdditionalInfo = "Username must be >=3 characters & Password must be >=8 characters";
        // final String signupErrorMessage = "Check that Username and Password are valid and that Username has not already been registered then try again.";
        // hint at bottom of login form to clarify what's valid 
        
        // Center the signupForm
        setHorizontalComponentAlignment(Alignment.CENTER, signupForm);

        add(signupForm);
    }
}