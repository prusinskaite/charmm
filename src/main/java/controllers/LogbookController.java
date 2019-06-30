package controllers;

import entities.Administration;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
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
import java.util.TimeZone;

public class LogbookController {
    /**
     * The container of all sliders.
     */
    @FXML
    public VBox sliderContainer;
    /**
     * Label for displaying patient info.
     */
    @FXML
    public Label patientLabel;

    /**
     * Generates sliders with labels for each administration of the currently selected patient.
     */
    @FXML
    public void initialize() {
        patientLabel.setText(Selection.getSelectedPatient().toString());
        for (int i = 0; i < Selection.getSelectedPatient().getAdministrations().size(); i++) {
            Administration administration = Selection.getSelectedPatient().getAdministrations().get(i);
            //sets max to the time of administration + 2 hours (10800000 ms). This has to be a calculated value.
            //TODO
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
            Label medicine = new Label(Selection.getLanguage().getString("logbook.medicine") + administration.getMedicine().getName() + ", " +
                    administration.getConcentration() + " mol/m³" + "\n");
            Label prescriber = new Label(Selection.getLanguage().getString("logbook.prescriber") +
                    administration.getLatestRecord().getPrescriber().getFullName() + "\n");
            HBox labels = new HBox(medicine, prescriber);
            medicine.setPadding(new Insets(10, 10, 10, 10));
            prescriber.setPadding(new Insets(10, 10, 10, 10));
            VBox vBox = new VBox(labels, slider);
            sliderContainer.getChildren().add(vBox);
            sliderContainer.setPadding(new Insets(10));
        }
    }

    /**
     * Action listener for "Back" button.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void goBack(ActionEvent actionEvent) throws IOException {
        FXRouter.goTo("home", Selection.getLanguage());
    }

}
