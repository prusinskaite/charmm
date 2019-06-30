package controllers;

import entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import support.FXRouter;
import support.Selection;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class HomeController {
    /**
     * The container which holds navigation buttons.
     */
    @FXML
    public VBox containerButtons;
    /**
     * Label for displaying warning on invalid input to the patient field.
     */
    @FXML
    public Label invalidInputPatient;
    /**
     * The search field.
     */
    @FXML
    private TextField search;
    /**
     * ObservableList which holds all data pf the search field.
     */
    @FXML
    private final ObservableList<Patient> data;
    private AutoCompletionBinding<Patient> acbp;

    //Patients
    private Patient jacob_smith;
    private Patient isabella_johnson;
    private Patient ethan_williams;
    private Patient emma_jones;
    private Patient michael_brown;

    /**
     * Creates mockup data.
     */
    @Deprecated
    private void createData() {
        //Patients
        jacob_smith = new Patient("Jacob Smith", new GregorianCalendar(2019, Calendar.FEBRUARY, 11).getTime(), 001L);
        isabella_johnson = new Patient("Isabella Johnson", new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), 002L);
        ethan_williams = new Patient("Ethan Williams", new GregorianCalendar(2019, Calendar.MARCH, 20).getTime(), 003L);
        emma_jones = new Patient("Emma Jones", new GregorianCalendar(2019, Calendar.APRIL, 15).getTime(), 004L);
        michael_brown = new Patient("Michael Brown", new GregorianCalendar(2019, Calendar.MAY, 18).getTime(), 005L);

        //Employees
        Employee bob_smith = new Employee("Bob Smith", new GregorianCalendar(1990, Calendar.FEBRUARY, 12).getTime(), 201L);
        Employee robin_short = new Employee("Robin Short", new GregorianCalendar(1991, Calendar.FEBRUARY, 23).getTime(), 202L);
        Employee lenny_wilkins = new Employee("Lenny Wilkins", new GregorianCalendar(1993, Calendar.MAY, 18).getTime(), 203L);
        Employee remi_soto = new Employee("Remi Soto", new GregorianCalendar(1995, Calendar.JANUARY, 12).getTime(), 204L);

        //Medicines
        Medicine lisinopril = new Medicine(301L, "Lisinopril");
        Medicine levothyroxine = new Medicine(302L, "Levothyroxine");
        Medicine azithromycin = new Medicine(303L, "Azithromycin");
        Medicine metformin = new Medicine(304L, "Metformin");
        Medicine lipitor = new Medicine(305L, "Lipitor");
        Medicine amlodipine = new Medicine(306L, "Amlodipine");
        Medicine amoxicillin = new Medicine(307L, "Amoxicillin");
        Medicine hydrochlorothiazide = new Medicine(308L, "Hydrochlorothiazide");
        Medicine caffeine = new Medicine(309L, "Caffeine");
        Medicine glucophage = new Medicine(310L, "Glucophage");
        Medicine zocor = new Medicine(311L, "Zocor");

        //Administrations
        Date now = new Date();
        Administration a1 = new Administration(401, lisinopril, 10.0, jacob_smith);
        Record r1 = new Record(new Date(now.getTime() - 1000 * 60 * 60 * 1), lenny_wilkins, 1.0);
        a1.addRecord(r1);
        Administration a2 = new Administration(402, caffeine, 10.0, jacob_smith);
        Record r2 = new Record(new Date(now.getTime() - 1000 * 60 * 60 / 2), bob_smith, 2.0);
        a2.addRecord(r2);
        Administration a3 = new Administration(403, amoxicillin, 10.0, jacob_smith);
        Record r3 = new Record(new Date(now.getTime() - 1000 * 60 * 60 * 2), remi_soto, 3.0);
        a3.addRecord(r3);

        Administration a4 = new Administration(404, zocor, 10.0, ethan_williams);
        Record r4 = new Record(new Date(now.getTime() - 1000 * 60 * 60 * 1), robin_short, 1.0);
        a4.addRecord(r4);

        Administration a5 = new Administration(405, lisinopril, 10.0, emma_jones);
        Record r5 = new Record(new Date(now.getTime() - 1000 * 60 * 60 * 1), lenny_wilkins, 2.0);
        Record r5a = new Record(new Date(now.getTime() - 1000), lenny_wilkins, 49.0);
        a5.addRecord(r5);
        a5.addRecord(r5a);
        Administration a6 = new Administration(406, levothyroxine, 10.0, emma_jones);
        Record r6 = new Record(new Date(now.getTime() - 1000 * 60 * 60 * 1 + 1000 * 60 * 30), lenny_wilkins, 3.0);
        a6.addRecord(r6);
        Administration a7 = new Administration(407, azithromycin, 10.0, emma_jones);
        Record r7 = new Record(new Date(now.getTime() - 1000 * 60 * 60 * 1 + 1000 * 60 * 20), lenny_wilkins, 4.0);
        a7.addRecord(r7);
        Administration a8 = new Administration(408, metformin, 10.0, emma_jones);
        Record r8 = new Record(new Date(now.getTime() - 1000 * 60 * 60 * 1 + 1000 * 60 * 10), remi_soto, 5.0);
        Administration a9 = new Administration(409, lipitor, 10.0, emma_jones);
        a8.addRecord(r8);
        Record r9 = new Record(new Date(now.getTime() - 1000 * 60 * 10), remi_soto, 6.0);
        a9.addRecord(r9);
        Administration a10 = new Administration(410, amlodipine, 10.0, emma_jones);
        Record r10 = new Record(new Date(now.getTime() - 1000 * 60 * 11), bob_smith, 7.0);
        a10.addRecord(r10);
        Administration a11 = new Administration(411, amoxicillin, 10.0, emma_jones);
        Record r11 = new Record(new Date(now.getTime() - 1000 * 60 * 25), bob_smith, 8.0);
        a11.addRecord(r11);
        Administration a12 = new Administration(412, hydrochlorothiazide, 10.0, emma_jones);
        Record r12 = new Record(new Date(now.getTime() - 1000 * 60 * 32), robin_short, 9.0);
        a12.addRecord(r12);
        Administration a13 = new Administration(413, caffeine, 10.0, emma_jones);
        Record r13 = new Record(new Date(now.getTime() - 1000 * 60 * 22), robin_short, 10.0);
        a13.addRecord(r13);
        Administration a14 = new Administration(414, glucophage, 10.0, emma_jones);
        Record r14 = new Record(new Date(now.getTime() - 1000 * 60 * 1), robin_short, 11.0);
        a14.addRecord(r14);

        emma_jones.setAdministrations(Arrays.asList(a5, a6, a7, a8, a9, a10, a11, a12, a13, a14));
        jacob_smith.setAdministrations(Arrays.asList(a1, a2, a3));
        ethan_williams.setAdministrations(Arrays.asList(a4));
    }

    /**
     * Initializes data and calls {@link #createData()}. When this class is created the constructor is being called
     * before the {@link #initialize()}, therefore data has to be created and assigned either here or when initialized.
     */
    public HomeController() {
        createData();
        data = FXCollections.observableArrayList(jacob_smith, isabella_johnson, ethan_williams, emma_jones, michael_brown);
    }

    /**
     * Initialize.
     */
    @FXML
    public void initialize() {
        initializeSearchField(data);
        //set warning label invisible.
        invalidInputPatient.setVisible(false);
    }

    /**
     * Initializes {@link #search}.
     *
     * @param list
     */
    public void initializeSearchField(ObservableList<Patient> list) {
        //if patient was already selected
        if (Selection.getSelectedPatient() != null) {
            search.setText(Selection.getSelectedPatient().toString());
            containerButtons.setVisible(true);
        }
        //if there is no data
        if (list.isEmpty()) {
            invalidInputPatient.setVisible(true);
            search.setPromptText(Selection.getLanguage().getString("home.noPatients"));
            TextFields.bindAutoCompletion(search, "");
        } else {
            invalidInputPatient.setVisible(false);
            acbp = TextFields.bindAutoCompletion(search, list);
            acbp.setOnAutoCompleted(p -> Selection.setSelectedPatient(p.getCompletion()));
        }
    }

    /**
     * Action listener for "Select button".
     *
     * @param actionEvent
     */
    public void selectPatient(ActionEvent actionEvent) {
        if (Selection.getSelectedPatient() != null) {
            invalidInputPatient.setVisible(false);
            containerButtons.setVisible(true);
        } else {
            invalidInputPatient.setVisible(true);
        }
    }

    /**
     * Action listener for "Logbook" button.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void goToLogbook(ActionEvent actionEvent) throws IOException {
        FXRouter.goTo("logbook", Selection.getLanguage());
    }

    /**
     * Action listener for "Graphs" button.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void goToGraphs(ActionEvent actionEvent) throws IOException {
        FXRouter.goTo("graphs", Selection.getLanguage());
    }

    /**
     * Action listener for "Administer" button.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void goToAdminister(ActionEvent actionEvent) throws IOException {
        FXRouter.goTo("administer", Selection.getLanguage());
    }

    /**
     * Action listener for "Logout" button.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void logout(ActionEvent actionEvent) throws IOException {
        Selection.setSelectedPatient(null);
        Selection.setCurrentUser(null);
        FXRouter.goTo("login", Selection.getLanguage());
    }
}
