package trackour.trackour;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;

@SpringBootApplication
@PWA(name = "trackour", shortName = "trackour")
@Theme(value = "trackour")
public class TrackourApplication implements AppShellConfigurator {

	public static void main(String[] args) {
		SpringApplication.run(TrackourApplication.class, args);
	}

}
