package com.sipios.refactoring.service;

import com.sipios.refactoring.exception.PriceException;
import com.sipios.refactoring.exception.UnexpectedBodyException;
import com.sipios.refactoring.model.ShoppingCart;
import com.sipios.refactoring.model.ShoppingItem;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Service
public class ShoppingServiceImpl implements ShoppingService {

    @Override
    public String getPrice(ShoppingCart shoppingCart) {
        {
            double price = 0;
            double discountRate;

            Date     date = new Date();
            Calendar calendar  = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
            calendar.setTime(date);

            // Compute discount for customer
            discountRate = getDiscountRate(shoppingCart);

            if (isShoppingCartEmpty(shoppingCart)) {
                return "0";
            }

            // Compute total amount depending on the types and quantity of product and
            // if we are in winter or summer discounts periods
            if (
                !isWinterDiscountPeriod(calendar) &&
                !isSummerDiscountPeriod(calendar)
            ) {
                for (int index = 0; index < shoppingCart.getItems().length; index++) {
                    ShoppingItem shoppingItem = shoppingCart.getItems()[index];

                    if (shoppingItem.getType().equals("TSHIRT")) {
                        price += 30 * shoppingItem.getNb() * discountRate;
                    } else if (shoppingItem.getType().equals("DRESS")) {
                        price += 50 * shoppingItem.getNb() * discountRate;
                    } else if (shoppingItem.getType().equals("JACKET")) {
                        price += 100 * shoppingItem.getNb() * discountRate;
                    }
                }
            } else {
                for (int index = 0; index < shoppingCart.getItems().length; index++) {
                    ShoppingItem it = shoppingCart.getItems()[index];

                    if (it.getType().equals("TSHIRT")) {
                        price += 30 * it.getNb() * discountRate;
                    } else if (it.getType().equals("DRESS")) {
                        price += 50 * it.getNb() * 0.8 * discountRate;
                    } else if (it.getType().equals("JACKET")) {
                        price += 100 * it.getNb() * 0.9 * discountRate;
                    }
                }
            }

            try {
                if (shoppingCart.getCustomerType().equals("STANDARD_CUSTOMER")) {
                    if (price > 200) {
                        throw new PriceException("Price (" + price + ") is too high for standard customer");
                    }
                } else if (shoppingCart.getCustomerType().equals("PREMIUM_CUSTOMER")) {
                    if (price > 800) {
                        throw new PriceException("Price (" + price + ") is too high for premium customer");
                    }
                } else if (shoppingCart.getCustomerType().equals("PLATINUM_CUSTOMER")) {
                    if (price > 2000) {
                        throw new PriceException("Price (" + price + ") is too high for platinum customer");
                    }
                } else {
                    if (price > 200) {
                        throw new PriceException("Price (" + price + ") is too high for standard customer");
                    }
                }
            } catch (Exception exception) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
            }

            return String.valueOf(price);
        }
    }

    private boolean isShoppingCartEmpty(ShoppingCart shoppingCart) {
        return shoppingCart.getItems() == null;
    }

    private double getDiscountRate(ShoppingCart shoppingCart) {
        double discountRate;
        if (shoppingCart.getCustomerType().equals("STANDARD_CUSTOMER")) {
            discountRate = 1;
        } else if (shoppingCart.getCustomerType().equals("PREMIUM_CUSTOMER")) {
            discountRate = 0.9;
        } else if (shoppingCart.getCustomerType().equals("PLATINUM_CUSTOMER")) {
            discountRate = 0.5;
        } else {
            throw new UnexpectedBodyException(HttpStatus.BAD_REQUEST);
        }
        return discountRate;
    }

    private boolean isSummerDiscountPeriod(Calendar calendar) {
        return calendar.get(Calendar.DAY_OF_MONTH) < 15 &&
        calendar.get(Calendar.DAY_OF_MONTH) > 5 &&
        calendar.get(Calendar.MONTH) == Calendar.JANUARY;
    }

    private boolean isWinterDiscountPeriod(Calendar calendar) {
        return calendar.get(Calendar.DAY_OF_MONTH) < 15 &&
        calendar.get(Calendar.DAY_OF_MONTH) > 5 &&
        calendar.get(Calendar.MONTH) == Calendar.JUNE;
    }
}
