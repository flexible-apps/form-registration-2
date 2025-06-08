package com.flexible.design.controller;
import com.flexible.design.utils.ScaleFactoryBuilder;
import io.github.palexdev.materialfx.beans.PositionBean;
import io.github.palexdev.materialfx.controls.MFXIconWrapper;
import io.github.palexdev.materialfx.effects.ripple.MFXCircleRippleGenerator;
import io.github.palexdev.materialfx.skins.base.MFXLabeledSkinBase;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

public class MFACheckboxSkin extends MFXLabeledSkinBase<MFACheckBox> {
    //================================================================================
    // Properties
    //================================================================================
    private final MFXIconWrapper box;

    private final StackPane rippleContainer;
    private final Circle rippleContainerClip;
    private final MFXCircleRippleGenerator rippleGenerator;

    //================================================================================
    // Constructors
    //================================================================================
    public MFACheckboxSkin(MFACheckBox checkbox) {
        super(checkbox);

//        MFXFontIcon mark = new MFXFontIcon();
        MFAGlyphView mark = new MFAGlyphView("M20 6L9 17L4 12", () -> ScaleFactoryBuilder.of(.6));
        mark.getStyleClass().add("mark");
        box = new MFXIconWrapper(mark, -1);
        box.getStyleClass().add("box");

        rippleContainer = new StackPane();
        rippleGenerator = new MFXCircleRippleGenerator(rippleContainer);
        rippleGenerator.setManaged(false);
        rippleGenerator.setAnimateBackground(false);
        rippleGenerator.setCheckBounds(false);
        rippleGenerator.setClipSupplier(() -> null);
        rippleGenerator.setRipplePositionFunction(event -> {
            PositionBean position = new PositionBean();
            position.setX(Math.min(event.getX(), rippleContainer.getWidth()));
            position.setY(Math.min(event.getY(), rippleContainer.getHeight()));
            return position;
        });

        rippleContainer.getChildren().addAll(rippleGenerator, box);
        rippleContainer.getStyleClass().add("ripple-container");

        rippleContainerClip = new Circle();
        rippleContainerClip.centerXProperty().bind(rippleContainer.widthProperty().divide(2.0));
        rippleContainerClip.centerYProperty().bind(rippleContainer.heightProperty().divide(2.0));
        rippleContainer.setClip(rippleContainerClip);

        updateAlignment();
        initContainer();

        getChildren().setAll(topContainer);
        addListeners();
    }

    //================================================================================
    // Overridden Methods
    //================================================================================
    @Override
    protected void addListeners() {
        super.addListeners();
        MFACheckBox checkbox = getSkinnable();

        checkbox.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            rippleGenerator.generateRipple(event);
            checkbox.fire();
        });
    }

    @Override
    protected void layoutChildren(double contentX, double contentY, double contentWidth, double contentHeight) {
        super.layoutChildren(contentX, contentY, contentWidth, contentHeight);

        double boxSize = box.getSize();
        Insets boxPadding = rippleContainer.getPadding();
        double boxClipRadius = boxPadding.getLeft() + boxSize / 2 + boxPadding.getRight();
        rippleContainerClip.setRadius(boxClipRadius);
    }

    @Override
    protected Pane getControlContainer() {
        return rippleContainer;
    }
}
