package controllers;

import entities.Employee;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.*;
import javafx.util.Duration;
import support.FXRouter;
import support.Selection;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class LoginController {
    /**
     * Animation duration time in ms.
     */
    public static final double ANIMATION_TIME = 3000;
    /**
     * The name of this scene.
     */
    public static final String NAME = "login";
    /**
     * Animation path transition.
     */
    public PathTransition anim;

    @FXML
    public TextField tfUsername;
    @FXML
    public TextField tfPassword;
    @FXML
    public Button back;
    /**
     * Container which holds available login options.
     */
    @FXML
    public HBox containerMethods;
    /**
     * Container which holds elements for password and username login.
     */
    @FXML
    public VBox containerPassword;
    /**
     * Container which holds elements for fingerprint login.
     */
    @FXML
    public StackPane containerFingerprint;
    /**
     * Container which holds elements for card login.
     */
    @FXML
    public StackPane containerCard;
    /**
     * Big label which tells what to do.
     */
    @FXML
    public Label masterLabel;
    /**
     * Label for displaying dev info.
     */
    @FXML
    public Label development;
    /**
     * Label for displaying invalid username or password warning.
     */
    @FXML
    public Label invalidUsernameOrPassword;
    /**
     * Label for displaying invalid card scan warning.
     */
    //TODO never use yet
    @FXML
    public Label invalidCard;
    /**
     * Label for displaying no match fingerprint warning.
     */
    //TODO never used yet.
    @FXML
    public Label invalidFinger;
    /**
     * Red horizontal rectangle.
     */
    @FXML
    public ImageView scannerF;
    /**
     * Red vertical rectangle.
     */
    @FXML
    public ImageView scannerC;
    /**
     * Fingerprint image view.
     */
    @FXML
    public ImageView fingerPrintPic;
    /**
     * Id card image view.
     */
    @FXML
    public ImageView idPic;
    /**
     * Button for switching to Dutch.
     */
    @FXML
    public Button langButtNL;
    /**
     * Button for switching to English.
     */
    @FXML
    public Button langButtEN;


    @FXML
    public void initialize() {
        //sets default language
        if (Selection.getCurrentLanguage() == null || Selection.getCurrentLanguage().equalsIgnoreCase("en")) {
            Selection.setLanguage("en");
            langButtEN.setVisible(false);
            langButtNL.setVisible(true);
        } else {
            Selection.setLanguage("nl");
            langButtEN.setVisible(true);
            langButtNL.setVisible(false);
        }
        selectMethod();
        invalidUsernameOrPassword.setVisible(false);
    }

    /**
     * Sets items visibility to display "Select method" state.
     * @param b
     */
    public void setMethodsContainersVisible(boolean b) {
        containerCard.setVisible(b);
        containerFingerprint.setVisible(b);
        containerPassword.setVisible(b);
    }

    /**
     * Login action listener.
     * @param event
     * @throws IOException
     */
    @FXML
    public void login(ActionEvent event) throws IOException {
        if (validUsernameAndPassword()) authorize();
        else handleValidationErrors();
    }

    /**
     * Handles authorisation.
     * @throws IOException
     */
    public void authorize() throws IOException {
        if (authorized()) {
            //TODO set proper user here
            Selection.setCurrentUser(new Employee("Lenny Wilkins", new GregorianCalendar(1993, Calendar.MAY, 18).getTime(), 203L));
            redirect();
        } else handleAuthorisationError();
    }

    /**
     * Redirects to home.
     * @throws IOException
     */
    public void redirect() throws IOException {
        FXRouter.goTo("home", Selection.getLanguage());
    }

    /**
     * Handles failed authorisation.
     */
    public void handleAuthorisationError() {
        //TODO
    }

    /**
     * Sets {@link #invalidUsernameOrPassword} to visible.
     */
    public void handleValidationErrors() {
        invalidUsernameOrPassword.setVisible(true);
    }

    /**
     * @return {@code true} if fields are not empty.
     */
    public boolean validUsernameAndPassword() {
        return (tfUsername.getText() != null && tfPassword.getText() != null) &&
                !(tfUsername.getText().isEmpty() && tfPassword.getText().isEmpty());
    }

    /**
     * @return {@code true} if fingerprint match was found, otherwise {@code false}.
     */
    public boolean validFinger() {
        //TODO implement finger print scanning
        return false;
    }

    /**
     * @return {@code true} if successfully authorised, otherwise {@code false}.
     */
    public boolean authorized() {
        //TODO compare username and password to db, if match true
        return true;
    }

    /**
     * The "Select method" state. Sets items visibility and labels text according to the needed state.
     */
    public void selectMethod() {
        development.setVisible(false);
        masterLabel.setText(Selection.getLanguage().getString("login.select"));
        setMethodsContainersVisible(false);
        containerMethods.setVisible(true);
        back.setVisible(false);
    }

    /**
     * Sets labels and visibility of items to display username and password method.
     *
     * @param actionEvent
     */
    @FXML
    public void renderTextFields(ActionEvent actionEvent) {
        containerMethods.setVisible(false);
        masterLabel.setText(Selection.getLanguage().getString("login.method.password"));
        setMethodsContainersVisible(false);
        containerPassword.setVisible(true);
        back.setVisible(true);
    }

    /**
     * Sets labels and visibility of items to display fingerprint method, plays animation.
     *
     * @param mouseEvent
     * @throws IOException
     * @throws InterruptedException
     */
    @FXML
    public void selectToScanFinger(MouseEvent mouseEvent) throws IOException, InterruptedException {
        containerMethods.setVisible(false);
        masterLabel.setText(Selection.getLanguage().getString("login.method.fingerprint"));
        setMethodsContainersVisible(false);
        containerFingerprint.setVisible(true);
        back.setVisible(true);
        animateScannerV();
        scanFinger();
    }


    /**
     * Action listener for "Scan fingerprint" button.
     *
     * @throws IOException
     * @throws InterruptedException
     */
    @FXML
    public void scanFinger() throws IOException, InterruptedException {
        development.setText(Selection.getLanguage().getString("login.devFinger"));
        development.setVisible(true);
        //TODO actually scan a finger and compare it to the db values
        if (validFinger()) authorize();
        else handleAuthorisationError();
    }

    /**
     * Action listener for "Scan card" button.
     *
     * @param actionEvent
     */
    @FXML
    public void scanCard(ActionEvent actionEvent) {
        development.setText(Selection.getLanguage().getString("login.devCard"));
        development.setVisible(true);
        containerMethods.setVisible(false);
        masterLabel.setText(Selection.getLanguage().getString("login.method.card"));
        setMethodsContainersVisible(false);
        containerCard.setVisible(true);
        back.setVisible(true);
        animateScannerH();
    }

    /**
     * Action listener for "Back" button.
     *
     * @param actionEvent
     */
    @FXML
    public void goBack(ActionEvent actionEvent) {
        selectMethod();
    }

    /**
     * Action listener for language switch.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void switchToEN(ActionEvent actionEvent) throws IOException {
        Selection.setLanguage("en");
        FXRouter.switchLanguage(Selection.getLanguage(), NAME);
        langButtEN.setVisible(false);
        langButtNL.setVisible(true);
    }

    /**
     * Action listener for language switch.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void switchToNL(ActionEvent actionEvent) throws IOException {
        Selection.setLanguage("nl");
        FXRouter.switchLanguage(Selection.getLanguage(), NAME);
        langButtEN.setVisible(true);
        langButtNL.setVisible(false);
    }

    /**
     * Returns vertical path for finger animation.
     *
     * @param x
     * @param y
     * @return vertical path for finger animation.
     */
    public PathElement[] getVPath(double x, double y) {
        x = (x + (scannerF.getFitWidth() / 2));
        PathElement[] path = {
                new MoveTo(x, y),
                new LineTo(x, y + (scannerF.getFitHeight() / 2) - (fingerPrintPic.getFitHeight() / 2)),
                new LineTo(x, y + (scannerF.getFitHeight() / 2) + (fingerPrintPic.getFitHeight() / 2)),
                new ClosePath()
        };
        return path;
    }

    /**
     * Returns horizontal path for card animation.
     *
     * @param x
     * @param y
     * @return horizontal path for card animation.
     */
    public PathElement[] getHPath(double x, double y) {
        y = y + scannerC.getFitHeight() / 2;
        PathElement[] path = {
                new MoveTo(x, y),
                new LineTo(x + (scannerC.getFitWidth() / 2) - (idPic.getFitWidth() / 2), y),
                new LineTo(x + (scannerC.getFitWidth() / 2) + (idPic.getFitWidth() / 2), y),
                new ClosePath()
        };
        return path;
    }

    /**
     * Animates horizontal scanner.
     */
    public void animateScannerH() {
        animateScanner(scannerC, getHPath(scannerC.getX(), scannerC.getY()));
    }

    /**
     * Animates vertical scanner.
     */
    public void animateScannerV() {
        animateScanner(scannerF, getVPath(scannerF.getX(), scannerF.getY()));
    }

    /**
     * Animates scanner.
     *
     * @param scanner
     * @param path
     */
    public void animateScanner(ImageView scanner, PathElement[] path) {
        Path road = new Path();
        road.getElements().addAll(path);
        anim = new PathTransition();
        anim.setNode(scanner);
        anim.setPath(road);
        anim.setInterpolator(Interpolator.LINEAR);
        anim.setDuration(new Duration(ANIMATION_TIME));
        anim.setCycleCount(Timeline.INDEFINITE);
        anim.play();
    }
}
