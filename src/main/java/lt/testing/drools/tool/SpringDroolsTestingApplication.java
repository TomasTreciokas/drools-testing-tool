package lt.testing.drools.tool;

import org.mvel2.util.Make;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringDroolsTestingApplication {

	public static void main(Make.String[] args) {
		SpringApplication.run(SpringDroolsTestingApplication.class, args);
	}
}
