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

public class AdministerController {

    /**
     * Label to display Patient info.
     */
    @FXML
    public Label patientLabel;
    /**
     * Table which holds administrations and the last record.
     */
    @FXML
    private TableView<Administration> administerTable;
    /**
     * Medicine column.
     */
    @FXML
    private TableColumn<Administration, String> columnMedicine;
    /**
     * Latest record dosage column.
     */
    @FXML
    private TableColumn<Administration, String> columnOldDosage;
    /**
     * Editable new dosage column.
     */
    @FXML
    private TableColumn<Administration, String> columnNewDosage;
    /**
     * ObservableList which holds all the data which is used in the table.
     */
    private ObservableList<Administration> masterData = FXCollections.observableArrayList();
    /**
     * Mockup safe dosage limit. Must be removed when real safe limits can be calculated.
     */
    @Deprecated
    private Double safeLimit = 50.0;

    /**
     * Initializes the view. Sets data for {@link #patientLabel}, {@link #masterData}, {@link #columnMedicine},
     * {@link #columnOldDosage}; creates Callback, sets {@link #columnNewDosage} functionality/
     */
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
     * Action listener for "Submit" button. Creates and shows alert message.
     *
     * @param actionEvent
     */
    public void submit(ActionEvent actionEvent) {
        Alert saved = new Alert(Alert.AlertType.ERROR);
        saved.setHeaderText("New dosage has been administered.");
        saved.setContentText("By: " + Selection.getCurrentUser().getFullName() + "\nTo: " + Selection.getSelectedPatient().toString() + "\nAt: " + new Date().toString());
        saved.showAndWait();
    }

    /**
     * Custom editable cell class.
     */
    class EditingCell extends TableCell<Administration, String> {
        /**
         * Editable text field.
         */
        private TextField textField;

        /**
         * Default constructor.
         */
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

        /**
         * Creates text field, sets onKeyPressed, adds listener.
         */
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
                            //if value is more than safe limit
                            Double.valueOf(newValue).compareTo(safeLimit) >= 0) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setHeaderText("Dangerous dosage!");
                        errorAlert.setContentText("The safe dosage limit \"" + safeLimit + "\" has been exceeded.");
                        errorAlert.showAndWait();
                    }
                }
            });
        }

        private String getString() {
            return getItem() == null ? "" : getItem();
        }
    }
}

