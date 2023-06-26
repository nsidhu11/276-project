package trackour.trackour.security;

import trackour.trackour.data.service.CustomUserDetailsServiceImplementation;
import trackour.trackour.views.login.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * Session-based authentication using {@linkplain VaadinWebSecurity}
 */
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends VaadinWebSecurity {

    CustomUserDetailsServiceImplementation customUserDetailsService;

    SecurityConfiguration(CustomUserDetailsServiceImplementation customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    /**
     * {@link DaoAuthenticationProvider} object to authenticate username & password   
     * @return {@link DaoAuthenticationProvider}
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        
        authProvider.setUserDetailsService(this.customUserDetailsService);
        authProvider.setPasswordEncoder(customUserDetailsService.passwordEncoder());
    
        return authProvider;
    }
    
    /**
     * Convert {@link AuthenticationManager} bean
     * @param authConfiguration
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
        return authConfiguration.getAuthenticationManager();
    }

    /**
     * {@summary} Custom filter chain configuration & {@link VaadinWebSecurity}
     *  handles the login logout behaviour
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http.authorizeHttpRequests(requests -> requests.requestMatchers("/xyz").permitAll());
        super.configure(http);
        
        // Register your login view to the view access checker mechanism
        setLoginView(http, LoginView.class,"/login");
    }
    
}
