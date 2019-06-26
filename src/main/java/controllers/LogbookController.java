package controllers;

import entities.Administration;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import support.FXRouter;
import support.Selection;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class LogbookController {
    @FXML
    public VBox sliderContainer;
    @FXML
    public Label patientLabel;
    @FXML
    private Map<Label, Slider> sliders = new HashMap<>();

    @FXML
    public void initialize() {
        patientLabel.setText(Selection.getSelectedPatient().toString());
        for (int i = 0; i < Selection.getSelectedPatient().getAdministrations().size(); i++) {
            Administration administration = Selection.getSelectedPatient().getAdministrations().get(i);
            Slider slider = new Slider(administration.getLatestRecord().getTime().getTime(), administration.getLatestRecord().getTime().getTime() + 10800000, System.currentTimeMillis());
            slider.setMajorTickUnit(450);
            slider.setShowTickLabels(true);
            slider.setStyle("-webkit-appearance: none;\n" +
                    "    width: 100%;\n" +
                    "    height: 15px;\n" +
                    "    border-radius: 5px;\n" +
                    "    background: #d3d3d3;\n" +
                    "    outline: none;\n" +
                    "    opacity: 0.7;\n" +
                    "    -webkit-transition: .2s;\n" +
                    "    transition: opacity .2s;");
            StringConverter<Double> stringConverter = new StringConverter<Double>() {
                @Override
                public String toString(Double object) {
                    Date date = new Date(Math.round(object));
                    DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                    formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
                    return formatter.format(date);
                }

                @Override
                public Double fromString(String string) {
                    return null;
                }
            };
            slider.setLabelFormatter(stringConverter);
            Text text = new Text();
            slider.valueProperty().addListener((observable, oldValue, newValue) ->
                    text.setText(stringConverter.toString(newValue.doubleValue())));
            Label medecine = new Label("Medicine: " + administration.getMedicine().getName() + "\n");
            Label prescriber = new Label("Prescriber: " + administration.getLatestRecord().getPrescriber().toString() + "\n");
            HBox labels = new HBox(medecine, prescriber);
            medecine.setPadding(new Insets(10, 10, 10, 10));
            prescriber.setPadding(new Insets(10, 10, 10, 10));
            VBox vBox = new VBox(labels, slider);
            sliderContainer.getChildren().add(vBox);
            sliderContainer.setPadding(new Insets(10));
        }
    }

    private Slider createSlider(Double value) {
        Slider slider = new Slider();
        slider.setValue(value);
        return slider;
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        FXRouter.goTo("home");
    }

}
