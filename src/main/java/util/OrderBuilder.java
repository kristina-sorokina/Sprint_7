package util;

import api.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderBuilder {
    public static Order getOrderBlackColor() {
        return Order.builder()
                .firstName("KateTest")
                .lastName("SmithTest")
                .address("Test address 15")
                .metroStation("Arbat")
                .phone("12545859699")
                .rentTime(4)
                .deliveryDate("2023-01-25")
                .comment("This is a test comment")
                .color(new ArrayList<>(List.of("BLACK")))
                .build();
    }

    public static Order getOrderNoColor() {
        return Order.builder()
                .firstName("KateTest")
                .lastName("SmithTest")
                .address("Test address 15")
                .metroStation("Arbat")
                .phone("12545859699")
                .rentTime(4)
                .deliveryDate("2023-01-25")
                .comment("This is a test comment")
                .color(null)
                .build();
    }

    public static Order getOrderGreyColor() {
        return Order.builder()
                .firstName("KateTest")
                .lastName("SmithTest")
                .address("Test address 15")
                .metroStation("Arbat")
                .phone("12545859699")
                .rentTime(4)
                .deliveryDate("2023-01-25")
                .comment("This is a test comment")
                .color(new ArrayList<>(List.of("GREY")))
                .build();
    }

    public static Order getOrderBlackAndGreyColor() {
        return Order.builder()
                .firstName("KateTest")
                .lastName("SmithTest")
                .address("Test address 15")
                .metroStation("Arbat")
                .phone("12545859699")
                .rentTime(4)
                .deliveryDate("2023-01-25")
                .comment("This is a test comment")
                .color(new ArrayList<>(List.of("BLACK", "GREY")))
                .build();
    }
}
