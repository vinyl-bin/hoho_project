package hoho_project.hoho;

import hoho_project.hoho.controller.WebMvcConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(WebMvcConfig.class)
public class HohoApplication {

	public static void main(String[] args) {
		SpringApplication.run(HohoApplication.class, args);
	}

}
