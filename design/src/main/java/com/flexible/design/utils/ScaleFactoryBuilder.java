package com.flexible.design.utils;

import javafx.beans.NamedArg;

import java.util.Objects;

public class ScaleFactoryBuilder {

    public static final ScaleFactoryBuilder DEFAULT = new ScaleFactoryBuilder(1.0);

    private final double x;
    private final double y;

    public static ScaleFactoryBuilder of(double xy) {
        return new ScaleFactoryBuilder(xy);
    }

    public static ScaleFactoryBuilder of(double x, double y) {
        return new ScaleFactoryBuilder(x, y);
    }

    public ScaleFactoryBuilder(@NamedArg("x") double x, @NamedArg("y") double y) {
        this.x = x;
        this.y = y;
    }

    public ScaleFactoryBuilder(@NamedArg("xy") double xy) {
        this.x = xy;
        this.y = xy;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScaleFactoryBuilder that)) return false;
        return Double.compare(x, that.x) == 0 && Double.compare(y, that.y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ScaleBuilder{");
        sb.append("x=").append(x);
        sb.append(", y=").append(y);
        sb.append('}');
        return sb.toString();
    }
}
