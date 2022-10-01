package order;

import io.qameta.allure.Step;

import java.util.ArrayList;
import java.util.List;

public class OrderColors {

    @Step("Create List colors")
    public static List<List<String>> getColor() {
        List<List<String>> colors = new ArrayList<>();
        colors.add(List.of("BLACK"));
        colors.add(List.of("GREY"));
        colors.add(List.of("BLACK", "GREY"));
        colors.add(List.of());
        colors.add(List.of(""));
        return colors;
    }
}
