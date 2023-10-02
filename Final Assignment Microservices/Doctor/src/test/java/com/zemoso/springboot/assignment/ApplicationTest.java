package com.zemoso.springboot.assignment;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class ApplicationTest {

    @Test
    void restTemplate() {
        Application application = new Application();

        // Act
        RestTemplate restTemplate = application.restTemplate();

        // Assert
        assertNotNull(restTemplate);
    }
    @Test
    void main() {
        Application.main(new String[]{});
    }
}
