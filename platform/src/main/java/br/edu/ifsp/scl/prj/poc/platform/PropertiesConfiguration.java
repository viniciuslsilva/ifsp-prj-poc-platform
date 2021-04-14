package br.edu.ifsp.scl.prj.poc.platform;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertiesConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "fake.external.service")
    public FakeExternalServiceProperties fakeExternalServiceProperties() {
        return new FakeExternalServiceProperties();
    }
}
