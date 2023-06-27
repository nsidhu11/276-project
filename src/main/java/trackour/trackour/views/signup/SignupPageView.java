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
        
        CustomSignupForm signupForm = new CustomSignupForm(userService);
        
        // Center the signupForm
        setHorizontalComponentAlignment(Alignment.CENTER, signupForm);

        add(signupForm);
    }
}