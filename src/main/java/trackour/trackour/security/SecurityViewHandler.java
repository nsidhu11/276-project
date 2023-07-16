package trackour.trackour.security;

import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.spring.security.AuthenticationContext;

import trackour.trackour.model.Role;

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
     * anonymous or unauthenticated users and admins only
     * 
     */
    public void handleAnonymousOnly(BeforeEnterEvent beforEnterEvent, Boolean excludeForAdmin){
        // only anonymous user sessions and admins are allowed
        boolean isAuthenticatedUser = getRequestSession().isPresent();
        Optional<UserDetails> userSession = getRequestSession();
        boolean isUserAdmin = false;
        
        if (userSession.isPresent()){
            isUserAdmin = userSession.get().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + Role.ADMIN.getName()));
        }
        System.out.println("isUserAdmin: " + isUserAdmin);

        if (isAuthenticatedUser){
            // allow this page to bypass the redirection protocol for admins
            if (excludeForAdmin && isUserAdmin) return;
            beforEnterEvent.rerouteTo("error");
        }
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
