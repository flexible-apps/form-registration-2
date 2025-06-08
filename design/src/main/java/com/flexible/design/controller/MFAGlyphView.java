package com.flexible.design.controller;

import com.flexible.design.utils.Call;
import com.flexible.design.utils.ScaleConverter;
import com.flexible.design.utils.ScaleFactoryBuilder;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.css.*;
import javafx.css.converter.StringConverter;
import javafx.scene.shape.SVGPath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MFAGlyphView extends SVGPath {
    private static final String STYLE_CLASS = "mfa-glyph";

    private final PseudoClass SELECTED = PseudoClass.getPseudoClass("selected");

    private final BooleanProperty selected =
            new SimpleBooleanProperty(this, "isSelected", false);

    private final StyleableStringProperty glyph =
            new SimpleStyleableStringProperty(StyleableProperties.GLYPH,this, "Glyph", "");

    private final StyleableObjectProperty<ScaleFactoryBuilder> scaleFactory =
            new SimpleStyleableObjectProperty<>(StyleableProperties.SCALE_FACTORY,this, "Scale Factory Builder", ScaleFactoryBuilder.DEFAULT);

    public MFAGlyphView() {
        initialize();
    }

    public MFAGlyphView(String glyph) {
        setGlyph(glyph);
        initialize();
    }

    public MFAGlyphView(String glyph, Call<ScaleFactoryBuilder> scaleBean) {
        setGlyph(glyph);
        setScaleFactory(scaleBean.get());
        initialize();
    }

    private void initialize() {
        this.getStyleClass().setAll(STYLE_CLASS);
        this.setContent(getGlyph());
        glyphProperty().addListener((observable, oldValue, newValue) -> this.setContent(newValue));

        ScaleFactoryBuilder sf = getScaleFactory();
        updateScaleFactory(sf.getX(), sf.getY());
        scaleFactoryProperty().addListener((observableValue, scaleBuilder, t1)
                -> updateScaleFactory(t1.getX(), t1.getY()));

        pseudoClassStateChanged(SELECTED, isSelected());
        selectedProperty().addListener((observable, oldValue, newValue)
                -> pseudoClassStateChanged(SELECTED, isSelected()));
    }

    public boolean isSelected() {
        return selected.get();
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    private void updateScaleFactory(double x, double y) {
        this.setScaleX(x);
        this.setScaleY(y);
        System.out.println(x + "   ---    " + y);
    }

    public String getGlyph() {
        return glyph.get();
    }

    public StyleableStringProperty glyphProperty() {
        return glyph;
    }

    public void setGlyph(String glyph) {
        this.glyph.set(glyph);
    }

    public ScaleFactoryBuilder getScaleFactory() {
        return scaleFactory.get();
    }

    public StyleableObjectProperty<ScaleFactoryBuilder> scaleFactoryProperty() {
        return scaleFactory;
    }

    public void setScaleFactory(ScaleFactoryBuilder scaleFactory) {
        this.scaleFactory.set(scaleFactory);
    }

    private static class StyleableProperties {
        private static final CssMetaData<MFAGlyphView, String> GLYPH =
                new CssMetaData<>("-mfa-glyph",
                        StringConverter.getInstance(), "") {
                    @Override
                    public boolean isSettable(MFAGlyphView control) {
                        return !control.glyph.isBound();
                    }

                    @Override
                    public StyleableStringProperty getStyleableProperty(MFAGlyphView control) {
                        return control.glyphProperty();
                    }
                };

        private static final CssMetaData<MFAGlyphView, ScaleFactoryBuilder> SCALE_FACTORY =
                new CssMetaData<>("-mfa-scale-factory",
                        ScaleConverter.getInstance(), ScaleFactoryBuilder.DEFAULT) {
                    @Override
                    public boolean isSettable(MFAGlyphView control) {
                        return !control.glyph.isBound();
                    }

                    @Override
                    public StyleableObjectProperty<ScaleFactoryBuilder> getStyleableProperty(MFAGlyphView control) {
                        return control.scaleFactoryProperty();
                    }
                };

        private static final List<CssMetaData<? extends Styleable, ?>> CHILD_STYLEABLES;

        static {
            final List<CssMetaData<? extends Styleable, ?>> styleables =
                    new ArrayList<>(SVGPath.getClassCssMetaData());
            Collections.addAll(styleables, GLYPH, SCALE_FACTORY);
            CHILD_STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return StyleableProperties.CHILD_STYLEABLES;
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
        return getClassCssMetaData();
    }
}
