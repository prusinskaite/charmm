package controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import support.FXRouter;
import support.Selection;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class GraphsController {
    private static final String BASE_PATH = "C:\\Users\\prush\\IdeaProjects\\charmm\\src\\main\\resources\\graphs\\";
    @FXML
    public Label patientLabel;
    private ToggleGroup group;
    @FXML
    public ImageView graph;
    @FXML
    public ToggleButton temp;
    @FXML
    public ToggleButton mf;
    @FXML
    public ToggleButton rho;
    @FXML
    public ToggleButton dp;
    @FXML
    private String path;

    @FXML
    public void initialize() {
        //initialize patient label
        patientLabel.setText(Selection.getSelectedPatient().toString());
        //initializing animation
        final Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(993),
                        event -> {
                            try {
                                graph.setImage(new Image(new BufferedInputStream(new FileInputStream(new File(getPath())))));
                            } catch (Exception e) {
                                System.err.println(e.getMessage());
                            }
                        }
                )
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        path= BASE_PATH + "Temp.jpeg";
        timeline.play();

        //initializing toggle button group
        group = new ToggleGroup();
        mf.setToggleGroup(group);
        dp.setToggleGroup(group);
        rho.setToggleGroup(group);
        temp.setToggleGroup(group);
        temp.setSelected(true);
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        FXRouter.goTo("home");
    }

    public void displayPressure(ActionEvent actionEvent) throws IOException {
        path = BASE_PATH + "DP.jpeg";
    }

    public String getPath() {
        return path;
    }

    public void displayTemperature(ActionEvent actionEvent) {
        path = BASE_PATH + "Temp.jpeg";
    }

    public void displayMassFlow(ActionEvent actionEvent) {
        path = BASE_PATH + "MF.jpeg";
    }

    public void displayDensity(ActionEvent actionEvent) {
        path = BASE_PATH + "Rho.jpeg";
    }
}
