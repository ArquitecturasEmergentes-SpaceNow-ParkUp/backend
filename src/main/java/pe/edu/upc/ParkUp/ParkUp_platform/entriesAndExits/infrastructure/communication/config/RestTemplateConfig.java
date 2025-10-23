package pe.edu.upc.ParkUp.ParkUp_platform.entriesAndExits.infrastructure.communication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplateConfig
 * <p>
 *     This configuration class provides RestTemplate beans for HTTP communication
 *     with Edge Nodes and external services.
 * </p>
 */
@Configuration
public class RestTemplateConfig {

    /**
     * RestTemplate bean for Edge Node communication
     * @return configured RestTemplate instance
     */
    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000); // 5 seconds connection timeout
        factory.setReadTimeout(5000);    // 5 seconds read timeout
        return new RestTemplate(factory);
    }
}
