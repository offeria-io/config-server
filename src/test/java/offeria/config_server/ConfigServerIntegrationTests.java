package offeria.config_server;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
public class ConfigServerIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void healthCheckIsPublic() {
        ResponseEntity<String> response = restTemplate.getForEntity("/actuator/health", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void configEndpointsRequireAuthentication() {
        // Try to access a generic config without credentials
        ResponseEntity<String> response = restTemplate.getForEntity("/application/dev", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void configEndpointsAccessibleWithCorrectCredentials() {
        // Access config with default credentials defined in application.yaml
        ResponseEntity<String> response = restTemplate.withBasicAuth("root", "s3cr3t")
                .getForEntity("/application/dev", String.class);
        
        // Even if the git repo is not cloned, it should be either OK or error but NOT Unauthorized
        assertThat(response.getStatusCode()).isNotEqualTo(HttpStatus.UNAUTHORIZED);
    }
}
