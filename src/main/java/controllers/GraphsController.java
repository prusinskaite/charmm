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
    /**
     * The file path to the location where graphs are stored. Notice that path to the folder must end with \.
     */
    private static final String BASE_PATH = "TODO\\";
    /**
     * The frame rate used to update {@link #graph}. It is set to 503 ms for avoiding concurrency, which may occur due
     * to the fact that python generates graphs every 500 ms. It is advised to use odd number to minimize chance of
     * concurrency.
     */
    public static final int FRAME_RATE = 503;
    /**
     * Label for displaying patient info.
     */
    @FXML
    public Label patientLabel;
    /**
     * ImageView for holding graph image.
     */
    @FXML
    public ImageView graph;
    /**
     * Temperature toggle button.
     */
    @FXML
    public ToggleButton temp;
    /**
     * Mass flow toggle button.
     */
    @FXML
    public ToggleButton mf;
    /**
     * Density toggle button.
     */
    @FXML
    public ToggleButton rho;
    /**
     * Pressure graph.
     */
    @FXML
    public ToggleButton dp;
    /**
     * The absolute path to the currently displayed graph.
     */
    private String path;
    /**
     * Toggle button group.
     */
    private ToggleGroup group;

    @FXML
    public void initialize() {
        //initialize patient label
        patientLabel.setText(Selection.getSelectedPatient().toString());
        //initializing animation
        final Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(FRAME_RATE),
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
        path = BASE_PATH + "Temp.jpeg";
        timeline.play();

        //initializing toggle button group
        group = new ToggleGroup();
        mf.setToggleGroup(group);
        dp.setToggleGroup(group);
        rho.setToggleGroup(group);
        temp.setToggleGroup(group);
        temp.setSelected(true);
    }

    /**
     * Action listener for "Back" button. Loads home page.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void goBack(ActionEvent actionEvent) throws IOException {
        FXRouter.goTo("home", Selection.getLanguage());
    }

    /**
     * Returns absolute path to the pressure graph.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void displayPressure(ActionEvent actionEvent) throws IOException {
        path = BASE_PATH + "DP.jpeg";
    }

    /**
     * Returns absolute path to the temperature graph.
     *
     * @param actionEvent
     */
    public void displayTemperature(ActionEvent actionEvent) {
        path = BASE_PATH + "Temp.jpeg";
    }

    /**
     * Returns absolute path to the mass flow graph.
     *
     * @param actionEvent
     */
    public void displayMassFlow(ActionEvent actionEvent) {
        path = BASE_PATH + "MF.jpeg";
    }

    /**
     * Returns absolute path to the density graph.
     *
     * @param actionEvent
     */
    public void displayDensity(ActionEvent actionEvent) {
        path = BASE_PATH + "Rho.jpeg";
    }

    /**
     * @return
     */
    public String getPath() {
        return path;
    }
}
