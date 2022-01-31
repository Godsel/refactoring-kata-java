package com.sipios.refactoring.service;

import com.sipios.refactoring.UnitTest;
import com.sipios.refactoring.model.Body;
import com.sipios.refactoring.model.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

@UnitTest
public class ShoppingServiceImplTest {
    @InjectMocks
    private ShoppingServiceImpl shoppingService;

    @Test
    void should_not_throw() {
        Assertions.assertDoesNotThrow(
            () -> shoppingService.getPrice(new Body(new Item[] {}, "STANDARD_CUSTOMER"))
                                     );
    }
}
