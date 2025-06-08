package com.flexible.design.app;

import com.flexible.design.controller.MFAButtonRegistration;
import com.flexible.design.controller.MFACheckBox;
import com.flexible.design.controller.MFAGlyphView;
import com.flexible.design.utils.ScaleFactoryBuilder;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable {
    private Delta delta;

    private final Stage primaryStage;
    private final Scene scene;
    @FXML
    private MFXButton btnEmail;

    @FXML
    private MFXButton btnExit;

    @FXML
    private MFAButtonRegistration btnGoogle;

    @FXML
    private MFXButton btnPhone;

    @FXML
    private MFAButtonRegistration btnSing;

    @FXML
    private MFACheckBox checked;

    @FXML
    private MFXComboBox<String> comboPhones;

    @FXML
    private TextField fieldPhone;

    @FXML
    private Hyperlink loginLink;

    @FXML
    private Text policyText;

    @FXML
    private AnchorPane root;

    @FXML
    private Text tremsText;

    public RegistrationController(Stage primaryStage, Scene scene) {
        this.primaryStage = primaryStage;
        this.scene = scene;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnSing.setArrow(getGlyphArrow());
        btnGoogle.setIcon(getGoogleIcon());
        btnGoogle.setArrow(getGlyphArrow());

        // load all countries form file.txt
        loadingCountries();

        // first create clip or mask for root of this application
//        createClip();

        // add move event for this app
        rootEvents();

        // add action for close this application
        closeApplication();
    }

    private void loadingCountries() {
        Task<Response<List<String>>> task = new Task<>() {
            @Override
            protected Response<List<String>> call() throws Exception {
                try {
                    return new Response<>(true, loadCountries());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return new Response<>(false);
                }
            }
        };

        task.setOnSucceeded(workerStateEvent -> {
            Response<List<String>> result = (Response<List<String>>) workerStateEvent.getSource().getValue();
            if (result.isSuccess()) {
                List<String> data = result.getData();
                comboPhones.getItems().setAll(data);
                comboPhones.setValue(data.get(0));
            } else {
                comboPhones.setValue("Countries Null");
                comboPhones.getItems().clear();
            }
        });

        task.setOnFailed(workerStateEvent -> System.out.println("Error"));

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    private List<String> loadCountries() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(
                    "C:\\Users\\sms-info\\IdeaProjects\\Registarion\\src\\main\\resources\\files\\countries.text"
            ));

            List<String> strings = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                strings.add(line);
            }
            reader.close();
            return strings;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void closeApplication() {
        btnExit.setOnAction(actionEvent -> primaryStage.close());
    }

    private void rootEvents() {
        delta = new Delta();
        root.setOnMousePressed(event -> {
            delta.x = event.getSceneX();
            delta.y = event.getSceneY();
            delta.tx = root.getTranslateX();
            delta.ty = root.getTranslateY();
        });

        root.setOnMouseDragged(event -> {
            double diffX = event.getScreenX() - delta.x + delta.tx;
            double diffY = event.getScreenY() - delta.y + delta.ty;

            primaryStage.setX(diffX);
            primaryStage.setY(diffY);
        });
    }

    private void createClip() {
        Rectangle rectangle = new Rectangle(root.getPrefWidth(), root.getPrefHeight());
        rectangle.setArcWidth(30);
        rectangle.setArcHeight(30);
        root.setClip(rectangle);
    }

    @NotNull
    private ImageView getGoogleIcon() {
        return new ImageView(new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("/images/google.png")),
                15, 15, true, true)
        );
    }

    @NotNull
    private static MFAGlyphView getGlyphArrow() {
        return new MFAGlyphView("M21 12L14 5V9H3.8C3.51997 9 3.37996 9 3.273 9.0545C3.17892 9.10243 3.10243 9.17892 3.0545 9.273C3 9.37996 3 9.51997 3 9.8V14.2C3 14.48 3 14.62 3.0545 14.727C3.10243 14.8211 3.17892 14.8976 3.273 14.9455C3.37996 15 3.51997 15 3.8 15H14V19L21 12Z",
                () -> ScaleFactoryBuilder.of(.8));
    }

    static class Delta {
        private double x, y, tx,ty;
    }

    static class Response<T> {
        private T data;
        private boolean success;

        public Response(boolean success, T data) {
            this.data = data;
            this.success = success;
        }

        public Response(boolean success) {
            this.success = success;
        }

        public Response() {
        }


        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }
    }

}
