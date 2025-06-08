package com.flexible.design.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.utils.StyleablePropertiesUtils;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.css.*;
import javafx.scene.Node;
import javafx.scene.control.Skin;

import java.util.List;
import java.util.Objects;

public class MFAButtonRegistration extends MFXButton {
    private static final StyleablePropertyFactory<MFAButtonRegistration> FACTORY = new StyleablePropertyFactory<>(MFXButton.getClassCssMetaData());
    private final static String STYLE_CLASS_NAME = "mfa-button-registration";
    private final ObjectProperty<Node> arrow = new SimpleObjectProperty<>();
    private final ObjectProperty<Node> icon = new SimpleObjectProperty<>();


    public MFAButtonRegistration() {
        init();
    }

    public MFAButtonRegistration(String text) {
        super(text);
        init();
    }

    public MFAButtonRegistration(String text, Node graphic) {
        super(text);
        setIcon(graphic);
        init();
    }

    private void init() {
        getStyleClass().addAll("mfx-button", STYLE_CLASS_NAME);
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new MFAButtonRegistrationSkin(this);
    }

    public Node getIcon() {
        return icon.get();
    }

    public ObjectProperty<Node> iconProperty() {
        return icon;
    }

    public void setIcon(Node icon) {
        this.icon.set(icon);
    }

    public Node getArrow() {
        return arrow.get();
    }

    public ObjectProperty<Node> arrowProperty() {
        return arrow;
    }

    public void setArrow(Node arrow) {
        this.arrow.set(arrow);
    }


    private final StyleableDoubleProperty horizontalSpace = new SimpleStyleableDoubleProperty(
            StyleableProperties.HORIZONTAL_SPACE,
            this,
            "Horizontal Space",
            5.

    );

    private final StyleableDoubleProperty verticalSpace = new SimpleStyleableDoubleProperty(
            StyleableProperties.VERTICAL_SPACE,
            this,
            "Vertical Space",
            5.

    );


    public double getHorizontalSpace() {
        return horizontalSpace.get();
    }

    public StyleableDoubleProperty horizontalSpaceProperty() {
        return horizontalSpace;
    }

    public void setHorizontalSpace(double horizontalSpace) {
        this.horizontalSpace.set(horizontalSpace);
    }

    public double getVerticalSpace() {
        return verticalSpace.get();
    }

    public StyleableDoubleProperty verticalSpaceProperty() {
        return verticalSpace;
    }

    public void setVerticalSpace(double verticalSpace) {
        this.verticalSpace.set(verticalSpace);
    }

    private static class StyleableProperties {
        private static final List<CssMetaData<? extends Styleable, ?>> cssMetaDataList;

        private static final CssMetaData<MFAButtonRegistration, Number> HORIZONTAL_SPACE =
                FACTORY.createSizeCssMetaData(
                        "-mfa-horizontal-space",
                        MFAButtonRegistration::horizontalSpaceProperty,
                        5.
                );

        private static final CssMetaData<MFAButtonRegistration, Number> VERTICAL_SPACE =
                FACTORY.createSizeCssMetaData(
                        "-mfa-vertical-space",
                        MFAButtonRegistration::verticalSpaceProperty,
                        5.
                );

        static {
            cssMetaDataList = StyleablePropertiesUtils.cssMetaDataList(
                    MFXButton.getClassCssMetaData(),
                    HORIZONTAL_SPACE, VERTICAL_SPACE
            );
        }

    }

    public static List<CssMetaData<? extends Styleable, ?>> getControlCssMetaDataList() {
        return StyleableProperties.cssMetaDataList;
    }


    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
        return getControlCssMetaDataList();
    }


    @Override
    public String getUserAgentStylesheet() {
        return Objects.requireNonNull(getClass().getResource("/css/MFAButtonRegistration.css")).toExternalForm();
    }

}
