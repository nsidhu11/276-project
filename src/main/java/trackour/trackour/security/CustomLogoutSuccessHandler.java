package trackour.trackour.security;
 
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
 
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler{

    @Override
    public void onLogoutSuccess(
        HttpServletRequest servletRequest,
         HttpServletResponse servletResponse,
          Authentication authenticatedInstance)
            throws IOException, jakarta.servlet.ServletException {
        if (authenticatedInstance != null && authenticatedInstance.getDetails() != null) {
            try {
                servletRequest.getSession().invalidate();
                System.out.println("Successfully Logged out a user!");
                // side effects of user logging out successfully can be programmed here
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
 
        // 200 response code
        servletResponse.setStatus(HttpServletResponse.SC_OK);
        //redirect to loginView
        servletResponse.sendRedirect("/login");
    }
}
