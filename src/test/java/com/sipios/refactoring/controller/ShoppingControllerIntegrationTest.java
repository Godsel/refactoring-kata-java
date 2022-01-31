package com.sipios.refactoring.controller;

import com.sipios.refactoring.IntegrationTest;
import com.sipios.refactoring.UnitTest;
import com.sipios.refactoring.model.Body;
import com.sipios.refactoring.model.Item;
import com.sipios.refactoring.service.ShoppingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@IntegrationTest
class ShoppingControllerIntegrationTest {

    @LocalServerPort
    private int localServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ShoppingController shoppingController;
    @Autowired
    private ShoppingService    shoppingService;

    @Test
    void should_not_throw() {
        String endPointUrl =
            "http://localhost:" + localServerPort + "/shopping";
        HttpEntity<String> stringHttpEntity = new HttpEntity<>(null,
                                                               null);
        ResponseEntity<String> stringResponseEntity = restTemplate.exchange(endPointUrl,
                                                                            HttpMethod.POST,
                                                                            stringHttpEntity,
                                                                            String.class);
        Assertions.assertDoesNotThrow(
            () -> shoppingService.getPrice(new Body(new Item[]{},
                                                    "STANDARD_CUSTOMER"))

                                     );
    }
}
