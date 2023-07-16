package trackour.trackour.views.forgotPassword;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "mail.smtp")
public class ResetConfig {
    Long port;

    public Long getPort() { return this.port; }
    public void setPort(Long port) { this.port = port; }

    // standard getters and setters
}