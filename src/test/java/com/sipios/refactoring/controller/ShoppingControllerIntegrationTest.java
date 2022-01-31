package com.sipios.refactoring.controller;

import com.sipios.refactoring.IntegrationTest;
import com.sipios.refactoring.model.ShoppingCart;
import com.sipios.refactoring.model.ShoppingItem;
import com.sipios.refactoring.service.ShoppingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

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
        org.junit.jupiter.api.Assertions.assertDoesNotThrow(
            () -> shoppingService.getPrice(new ShoppingCart(new ShoppingItem[]{},
                                                            "STANDARD_CUSTOMER"))

                                                           );
    }

    @Test
    void should_return_HttpOK_when_requesting_shopping_endpoint() {
        // Given
        ShoppingCart shoppingCart = new ShoppingCart(new ShoppingItem[]{}, "STANDARD_CUSTOMER");
        String endPointUrl =
            "http://localhost:" + localServerPort + "/shopping";
        HttpEntity<ShoppingCart> bodyHttpEntity = new HttpEntity<>(shoppingCart,
                                                                   new HttpHeaders());
        // when
        ResponseEntity<String> stringResponseEntity = restTemplate.exchange(
            endPointUrl,
            HttpMethod.POST,
            bodyHttpEntity,
            String.class);

        org.assertj.core.api.Assertions.assertThat(stringResponseEntity.getStatusCode())
                                       .isEqualTo(
                                           HttpStatus.OK);
    }

    @Test
    void should_return_HttpBadRequest_when_requesting_shopping_endpoint_with_wrong_object() {
        // Given
        ShoppingCart shoppingCart = new ShoppingCart(new ShoppingItem[]{}, "");
        String endPointUrl =
            "http://localhost:" + localServerPort + "/shopping";
        HttpEntity<ShoppingCart> bodyHttpEntity = new HttpEntity<>(shoppingCart,
                                                                   new HttpHeaders());
        // when
        ResponseEntity<String> stringResponseEntity = restTemplate.exchange(
            endPointUrl,
            HttpMethod.POST,
            bodyHttpEntity,
            String.class);

        org.assertj.core.api.Assertions.assertThat(stringResponseEntity.getStatusCode())
                                       .isEqualTo(
                                           HttpStatus.BAD_REQUEST);
    }
}
