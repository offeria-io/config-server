package offeria.config_server;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("dev") // Use dev profile for testing which uses a public sample repo
class ConfigServerApplicationTests {

	@Test
	void contextLoads() {
		// Basic test to verify Spring context loads correctly
	}

}
