package com.flexible.design.controller;

import io.github.palexdev.materialfx.skins.MFXButtonSkin;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

public class MFAButtonRegistrationSkin extends MFXButtonSkin {
    private final HBox container;
    private final HBox arrowContainer;
    private final HBox iconContainer;
    private final HBox textContainer;

    public MFAButtonRegistrationSkin(MFAButtonRegistration button) {
        super(button);

        container = createContainer();
        iconContainer = createIconBox();
        arrowContainer = createContainerArrow();

        checkIcon(button);
        button.iconProperty().addListener(observable -> checkIcon(button));

        checkArrow(button);
        button.arrowProperty().addListener(observable -> checkArrow(button));

        textContainer = createContainerText(container, iconContainer, arrowContainer);
        container.getChildren().setAll(iconContainer,textContainer, arrowContainer);


        updateChildren();
        Platform.runLater(this::updateNodes);
        getSkinnable().textProperty().addListener((observableValue) -> updateNodes());
    }

    private void checkArrow(MFAButtonRegistration skinnable) {
        if (skinnable.getArrow() != null) {
            arrowContainer.getChildren().setAll(skinnable.getArrow());
            System.out.println("add");
        } else {
            arrowContainer.getChildren().clear();
        }
    }

    private void checkIcon(MFAButtonRegistration button) {
        if (button.getIcon() != null) {
            iconContainer.setMinWidth(30);
            iconContainer.setPrefWidth(30);
            iconContainer.setMaxWidth(30);
            iconContainer.getChildren().setAll(button.getIcon());
        } else {
            iconContainer.setMinWidth(0);
            iconContainer.setPrefWidth(0);
            iconContainer.setMaxWidth(0);
            iconContainer.getChildren().clear();
        }
        iconContainer.getStyleClass().add("mfa-container-icon");
    }

    private HBox createContainerText(HBox container, HBox iconContainer, HBox arrowContainer) {
        HBox textContainer = new HBox();
        textContainer.setAlignment(Pos.CENTER_LEFT);

        DoubleBinding width = Bindings.createDoubleBinding(() -> container.getPrefWidth() - arrowContainer.getPrefWidth() - iconContainer.getPrefWidth(),
                container.prefWidthProperty(), container.widthProperty(), arrowContainer.prefWidthProperty(),
                iconContainer.prefHeightProperty(), iconContainer.widthProperty());

        DoubleBinding height = Bindings.createDoubleBinding(container::getPrefHeight,
                container.prefHeightProperty(), container.heightProperty(), arrowContainer.prefHeightProperty());

        applyBindings(textContainer, width, height);
//        textContainer.setStyle("-fx-background-color: #ddd555");
        textContainer.getStyleClass().add("mfa-container-text");
        return textContainer;
    }


    private void applyBindings(Region node, DoubleBinding widthBindings, DoubleBinding heightBindings) {
        node.minWidthProperty().bind(widthBindings);
        node.prefWidthProperty().bind(widthBindings);
        node.maxWidthProperty().bind(widthBindings);
        applyHeightBindings(node, heightBindings);
    }

    private void applyHeightBindings(Region node, DoubleBinding heightBindings) {
        node.minHeightProperty().bind(heightBindings);
        node.prefHeightProperty().bind(heightBindings);
        node.maxHeightProperty().bind(heightBindings);
    }


    private void updateNodes() {
        ObservableList<Node> children = getChildren();
        for (Node child : children) {
            if (child instanceof Text text) {
                text.getStyleClass().add("mfa-text");
                textContainer.getChildren().setAll(text);
                children.removeIf(node -> node.equals(text));
                children.add(container);
                break;
            }
        }
    }

    private HBox createContainer() {
        HBox container = new HBox();

        MFAButtonRegistration button = (MFAButtonRegistration) getSkinnable();

        DoubleBinding widthOfContainer = Bindings.createDoubleBinding(() -> button.getPrefWidth() - (button.getHorizontalSpace() * 2),
                button.prefWidthProperty(), button.widthProperty(), button.horizontalSpaceProperty());

        DoubleBinding heightOfContainer = Bindings.createDoubleBinding(() -> button.getPrefHeight() - (button.getVerticalSpace() * 2),
                button.prefHeightProperty(), button.heightProperty(), button.verticalSpaceProperty());

        widthOfContainer.addListener(observable -> {
            container.setTranslateX((button.getPrefWidth() / 2) - (widthOfContainer.get() / 2));

        });

        heightOfContainer.addListener((observable) -> {
            container.setTranslateY((button.getPrefHeight() / 2));
        });

        applyBindings(container, widthOfContainer, heightOfContainer);


        container.setAlignment(Pos.CENTER_LEFT);
//        container.setStyle("-fx-background-color: #555eee!important;");
        container.getStyleClass().add("mfa-container");
        return container;
    }

    private HBox boxes() {
        HBox box = new HBox();
        MFAButtonRegistration buttonRegistration = (MFAButtonRegistration) getSkinnable();
        DoubleBinding heightOfContainer = Bindings.createDoubleBinding(() -> container.getPrefHeight() - (buttonRegistration.getVerticalSpace() * 2),
                container.prefHeightProperty(), container.heightProperty(), buttonRegistration.verticalSpaceProperty());

        applyHeightBindings(box, heightOfContainer);
        box.setAlignment(Pos.CENTER);

        return box;
    }

    private HBox createContainerArrow() {
        HBox boxArrow = boxes();
        boxArrow.setMinWidth(30);
        boxArrow.setPrefWidth(30);
        boxArrow.setMaxWidth(30);
//        boxArrow.setStyle("-fx-background-color: red!important;");
        boxArrow.getStyleClass().add("mfa-container-arrow");
        return boxArrow;
    }

    private HBox createIconBox() {
        return boxes();
    }


    @Override
    protected void updateChildren() {
        super.updateChildren();
    }
}
