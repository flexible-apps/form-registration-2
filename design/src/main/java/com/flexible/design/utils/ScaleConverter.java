package com.flexible.design.utils;

import javafx.css.ParsedValue;
import javafx.css.StyleConverter;
import javafx.scene.text.Font;

import java.util.stream.Stream;

public class ScaleConverter extends StyleConverter<String, ScaleFactoryBuilder> {
    private static class Holder {
        static final ScaleConverter INSTANCE = new ScaleConverter();
    }

    public static StyleConverter<String, ScaleFactoryBuilder> getInstance() {
        return Holder.INSTANCE;
    }


    private ScaleConverter() {
        super();
    }

    @Override
    public ScaleFactoryBuilder convert(ParsedValue<String, ScaleFactoryBuilder> value, Font font) {
        double[] arr = Stream.of(value.getValue().split(" ")).mapToDouble(Double::parseDouble)
                .toArray();
        ScaleFactoryBuilder s = switch (arr.length) {
            case 0 -> ScaleFactoryBuilder.DEFAULT;
            case 1 -> new ScaleFactoryBuilder(arr[0]);
            case 2 -> new ScaleFactoryBuilder(arr[0], arr[1]);
            default -> throw new IllegalStateException("Unexpected value: " + arr.length);
        };
        System.out.println(s.toString());
        return s;
    }

    @Override
    public String toString() {
        return "ScaleBuilderConverter";
    }
}
