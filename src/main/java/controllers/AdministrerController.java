package controllers;

import entities.Administration;
import entities.Record;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.util.Callback;
import support.FXRouter;
import support.Selection;

import java.io.IOException;
import java.util.Date;

public class AdministrerController {
    @FXML
    public Label patientLabel;
    @FXML
    private TableView<Administration> administerTable;
    @FXML
    private TableColumn<Administration, String> columnMedicine;
    @FXML
    private TableColumn<Administration, String> columnOldDosage;
    @FXML
    private TableColumn<Administration, String> columnNewDosage;

    private ObservableList<Administration> masterData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        patientLabel.setText(Selection.getSelectedPatient().toString());
        masterData.addAll(Selection.getSelectedPatient().getAdministrations());
        columnMedicine.setCellValueFactory(cellData -> cellData.getValue().getMedicine().nameProperty());
        columnOldDosage.setCellValueFactory(cellData -> cellData.getValue().lastRecordProperty());
        Callback<TableColumn<Administration, String>, TableCell<Administration, String>> cellFactory =
                new Callback<TableColumn<Administration, String>, TableCell<Administration, String>>() {
                    public TableCell call(TableColumn p) {
                        return new EditingCell();
                    }
                };
        columnNewDosage.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Administration, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Administration, String> t) {
                        if (t.getNewValue().matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                            Selection.getSelectedPatient().getAdministrationByMedicine(((Administration) t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())).getMedicine()).addRecord(new Record(
                                    new Date(), Selection.getCurrentUser(), Double.valueOf(t.getNewValue())));
                        }
                    }
                });
        columnNewDosage.setCellFactory(cellFactory);
        administerTable.setItems(masterData);
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        FXRouter.goTo("home");
    }

    public void submit(ActionEvent actionEvent) {
        Alert saved = new Alert(Alert.AlertType.ERROR);
        saved.setHeaderText("New dosage has been administered.");
        saved.setContentText("By: " + Selection.getCurrentUser().getFullName() + "\nTo: " + Selection.getSelectedPatient().toString() + "\nAt: " + new Date().toString());
        saved.showAndWait();
    }

    class EditingCell extends TableCell<Administration, String> {

        private TextField textField;

        public EditingCell() {
        }

        @Override
        public void startEdit() {
            super.startEdit();
            if (textField == null) {
                createTextField();
            }
            setGraphic(textField);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            textField.selectAll();
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();

            setText(String.valueOf(getItem()));
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(textField);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setGraphic(textField);
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                } else {
                    setText(getString());
                    setContentDisplay(ContentDisplay.TEXT_ONLY);
                }
            }
        }

        private void createTextField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            textField.setOnKeyPressed(t -> {
                if (t.getCode() == KeyCode.ENTER) {
                    commitEdit(textField.getText());
                } else if (t.getCode() == KeyCode.ESCAPE) {
                    cancelEdit();
                }
            });
            textField.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (newValue != null &&
                            !newValue.isEmpty() &&
                            newValue.matches("\\d{0,7}([\\.]\\d{0,4})?") &&
                            Double.valueOf(newValue).compareTo(50.0) >= 0) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setHeaderText("Dangerous dosage!");
                        errorAlert.setContentText("The safe dosage limit \"" + 50.0 + "\" has been exceeded.");
                        errorAlert.showAndWait();
                        //textField.clear();
                    }
                }
            });
        }

        private String getString() {
            return getItem() == null ? "" : getItem();
        }
    }
}

