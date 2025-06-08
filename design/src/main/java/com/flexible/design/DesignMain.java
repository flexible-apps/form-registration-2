package com.flexible.design;

import com.flexible.design.controller.MFAButtonRegistration;
import com.flexible.design.controller.MFACheckBox;
import com.flexible.design.controller.MFAGlyphView;
import com.flexible.design.utils.ScaleFactoryBuilder;
import com.jfoenix.svg.SVGGlyph;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DesignMain extends Application {
    public static void main(String[] args) {
       launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        MFAGlyphView graphic = new MFAGlyphView("M5.08203 16.0553L11.5977 9.53093C12.3672 8.76042 13.6272 8.75958 14.3977 9.52906L20.922 16.0447",
                () -> ScaleFactoryBuilder.of(.6));
        graphic.setRotate(-90);
        MFACheckBox box = new MFACheckBox("sssssssssssss");
        box.setTranslateX(20);
        box.setTranslateY(20);


        MFAButtonRegistration registration = new MFAButtonRegistration("Login");
        registration.setPrefSize(150, 50);
        registration.setTranslateX(200);
        registration.setTranslateY(200);


box.setOnAction(actionEvent -> {
    if (box.isSelected()) {
        registration.setArrow(graphic);
    } else {
        registration.setArrow(null);
    }
});


        Pane pane = new Pane(registration,box);
        pane.setPrefSize(500,450);


        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}