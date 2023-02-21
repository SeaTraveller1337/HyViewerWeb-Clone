package abdulrahman.alluqmani.HyViewerWeb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;

@SpringBootApplication
@Theme("common-theme")
public class HyViewerWebApplication implements AppShellConfigurator  {

	public static void main(String[] args) {
		SpringApplication.run(HyViewerWebApplication.class, args);
	}

}
