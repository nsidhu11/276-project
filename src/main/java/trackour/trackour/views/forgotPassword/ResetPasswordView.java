package trackour.trackour.views.forgotPassword;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.orderedlayout.FlexComponent;
//import com.vaadin.flow.component.button.Button;
//import com.vaadin.flow.component.html.H3;
//import com.vaadin.flow.component.html.Span;
//import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.BeforeLeaveEvent;
import com.vaadin.flow.router.BeforeLeaveObserver;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import trackour.trackour.models.CustomUserDetailsService;
import trackour.trackour.models.User;
import trackour.trackour.security.SecurityViewHandler;
//import trackour.trackour.views.signup.CustomSignupForm;

@Route("resetPassword/token")
@PageTitle("Reset Password")
@AnonymousAllowed

public class ResetPasswordView extends VerticalLayout implements BeforeLeaveObserver, BeforeEnterObserver, HasUrlParameter<String> {

    @Autowired
    SecurityViewHandler securityViewHandler;

    @Autowired
    CustomUserDetailsService userService;

    ResetPasswordForm resetPasswordForm;

    User user;

    String token;

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

    @Override
    public void setParameter(BeforeEvent event, String parameter){
        this.token = parameter;

        if (userService.getByPasswordResetToken(parameter).isPresent()) {
            this.user = userService.getByPasswordResetToken(parameter).get();

            this.resetPasswordForm = new ResetPasswordForm(userService, user);
        
            // Center the form
            setAlignItems(FlexComponent.Alignment.CENTER);

            add(resetPasswordForm);
        }
    }

    public ResetPasswordView(SecurityViewHandler securityViewHandler, CustomUserDetailsService userService) {
        this.userService = userService;
    }
    
}
