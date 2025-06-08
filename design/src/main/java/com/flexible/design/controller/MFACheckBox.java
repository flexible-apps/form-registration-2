package com.flexible.design.controller;

import io.github.palexdev.materialfx.controls.base.MFXLabeled;
import io.github.palexdev.materialfx.utils.StyleablePropertiesUtils;
import javafx.css.*;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Skin;

import java.util.List;
import java.util.Objects;

public class MFACheckBox extends CheckBox implements MFXLabeled {
    //================================================================================
    // Properties
    //================================================================================
    private static final StyleablePropertyFactory<MFACheckBox> FACTORY = new StyleablePropertyFactory<>(CheckBox.getClassCssMetaData());
    private final String STYLE_CLASS = "mfx-checkbox";

    //================================================================================
    // Constructors
    //================================================================================
    public MFACheckBox() {
        this("");
    }

    public MFACheckBox(String text) {
        super(text);
        initialize();
    }

    //================================================================================
    // Methods
    //================================================================================
    private void initialize() {
        getStyleClass().addAll(STYLE_CLASS, "mfa-checkbox");
    }

    //================================================================================
    // Stylesheet properties
    //================================================================================
    private final StyleableObjectProperty<ContentDisplay> contentDisposition = new SimpleStyleableObjectProperty<>(
            StyleableProperties.CONTENT_DISPOSITION,
            this,
            "contentDisposition",
            ContentDisplay.LEFT
    ) {
        @Override
        public StyleOrigin getStyleOrigin() {
            return StyleOrigin.USER_AGENT;
        }
    };

    private final StyleableDoubleProperty gap = new SimpleStyleableDoubleProperty(
            StyleableProperties.GAP,
            this,
            "gap",
            8.0
    );

    private final StyleableBooleanProperty textExpand = new SimpleStyleableBooleanProperty(
            StyleableProperties.TEXT_EXPAND,
            this,
            "textExpand",
            false
    );

    public ContentDisplay getContentDisposition() {
        return contentDisposition.get();
    }

    public StyleableObjectProperty<ContentDisplay> contentDispositionProperty() {
        return contentDisposition;
    }

    public void setContentDisposition(ContentDisplay contentDisposition) {
        this.contentDisposition.set(contentDisposition);
    }

    public double getGap() {
        return gap.get();
    }

    public StyleableDoubleProperty gapProperty() {
        return gap;
    }

    public void setGap(double gap) {
        this.gap.set(gap);
    }

    public boolean isTextExpand() {
        return textExpand.get();
    }

    public StyleableBooleanProperty textExpandProperty() {
        return textExpand;
    }

    public void setTextExpand(boolean textExpand) {
        this.textExpand.set(textExpand);
    }

    //================================================================================
    // CSSMetaData
    //================================================================================
    private static class StyleableProperties {
        private static final List<CssMetaData<? extends Styleable, ?>> cssMetaDataList;

        private static final CssMetaData<MFACheckBox, ContentDisplay> CONTENT_DISPOSITION =
                FACTORY.createEnumCssMetaData(
                        ContentDisplay.class,
                        "-mfx-content-disposition",
                        MFACheckBox::contentDispositionProperty,
                        ContentDisplay.LEFT
                );

        private static final CssMetaData<MFACheckBox, Number> GAP =
                FACTORY.createSizeCssMetaData(
                        "-mfx-gap",
                        MFACheckBox::gapProperty,
                        8.0
                );

        private static final CssMetaData<MFACheckBox, Boolean> TEXT_EXPAND =
                FACTORY.createBooleanCssMetaData(
                        "-mfx-text-expand",
                        MFACheckBox::textExpandProperty,
                        false
                );

        static {
            cssMetaDataList = StyleablePropertiesUtils.cssMetaDataList(
                    CheckBox.getClassCssMetaData(),
                    CONTENT_DISPOSITION, GAP, TEXT_EXPAND
            );
        }
    }

    @Override
    public String getUserAgentStylesheet() {
        return Objects.requireNonNull(getClass().getResource("/css/MFACheckBox.css")).toExternalForm();
    }

    public static List<CssMetaData<? extends Styleable, ?>> getControlCssMetaDataList() {
        return StyleableProperties.cssMetaDataList;
    }

    //================================================================================
    // Override Methods
    //================================================================================


    @Override
    protected Skin<?> createDefaultSkin() {
        return new MFACheckboxSkin(this);
    }

    @Override
    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
        return getControlCssMetaDataList();
    }
}
