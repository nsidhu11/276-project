package trackour.trackour.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.spring.security.AuthenticationContext;

/**
 * This class includes "View-manipulation" methods
 * such as one to logout user, and methods for rerouting
 * to logout current user with {@code logOut()}.
 */
@Component
public class SecurityViewHandler {

    private final AuthenticationContext authContext;

    SecurityViewHandler (AuthenticationContext authContext) { this.authContext = authContext; }

    /**
     * Call this method to set any view as only accessible by
     * anonymous or unauthenticated users only
     * 
     */
    public void handleAnonymousOnly(BeforeEnterEvent beforEnterEvent, Boolean isAnonymousOnly, String rerouteTo){
        if (isAnonymousOnly && getRequestSession().isPresent()) {
            // this view should be only accessible to Anonymous users. Reroute if user is authenticated
            beforEnterEvent.rerouteTo(rerouteTo);
        }
        return;
    }

    public void logOut(){
        UI.getCurrent().getPage().setLocation("/login");
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(
                VaadinServletRequest.getCurrent().getHttpServletRequest(),null,null
        );
    }
    
    public Optional<UserDetails> getRequestSession() {
        return authContext.getAuthenticatedUser(UserDetails.class);
    }
}
