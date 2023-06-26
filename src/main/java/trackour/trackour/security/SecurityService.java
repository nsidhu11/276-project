package trackour.trackour.security;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.vaadin.flow.spring.security.AuthenticationContext;

// import com.vaadin.flow.spring.security.AuthenticationContext;

/**
 * {@summary Authentication info and helpers}
 * Inject into the constructor of a view that needs to access
 * current authenticated user with {@code getAuthenticatedUser()} info 
 * Other dependecy injections will do to 
 */
@Component
@Service
public class SecurityService {

    private final AuthenticationContext authContext;

    SecurityService (AuthenticationContext authContext) {
        this.authContext = authContext;
    }
    
    /**
     * Call to get current authenticated user info
     * @return Optional with current authenticated user, else empty 
     */
    public UserDetails getAuthenticatedUser() {
        return authContext.getAuthenticatedUser(UserDetails.class).get();
    }

}
