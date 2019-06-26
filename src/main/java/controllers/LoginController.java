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
import java.util.ResourceBundle;

public class LoginController {

    public static final double ANIMATION_TIME = 3000;
    public String lang;
    public static final String NAME = "login";
    public PathTransition anim;

    @FXML
    public TextField tfUsername;
    @FXML
    public TextField tfPassword;
    @FXML
    public Button back;
    @FXML
    public Button langButt;
    @FXML
    public HBox containerMethods;
    @FXML
    public VBox containerPassword;
    @FXML
    public StackPane containerFingerprint;
    @FXML
    public StackPane containerCard;
    @FXML
    public Label masterLabel;
    @FXML
    public Label development;
    @FXML
    public Label invalidUsernameOrPassword;
    @FXML
    public Label invalidCard;
    @FXML
    public Label invalidFinger;
    @FXML
    public ImageView scannerF;
    @FXML
    public ImageView scannerC;
    @FXML
    public ImageView fingerPrintPic;
    @FXML
    public ImageView idPic;


    @FXML
    public void initialize() {
        selectMethod();
        invalidUsernameOrPassword.setVisible(false);
    }


    public void setMethodsContainersVisible(boolean b) {
        containerCard.setVisible(b);
        containerFingerprint.setVisible(b);
        containerPassword.setVisible(b);
    }

    @FXML
    public void login(ActionEvent event) throws IOException {
        if (validUsernameAndPassword()) authorize();
        else handleValidationErrors();
    }

    @FXML
    public void switchLang() throws IOException {
        FXRouter.switchLanguage(getLang(), NAME);
    }

    public void authorize() throws IOException {
        if (authorized()) {
            //TODO set proper user here
            Selection.setCurrentUser(new Employee("Lenny Wilkins", new GregorianCalendar(1993, Calendar.MAY, 18).getTime(), 203L));
            redirect();
        } else handleAuthorisationError();
    }

    public void redirect() throws IOException {
        FXRouter.goTo("home", FXRouter.getBundle(getLang().equalsIgnoreCase("nl") ? "en" : "nl"));
    }

    public void handleAuthorisationError() {
        //TODO
    }

    public void handleValidationErrors() {
        invalidUsernameOrPassword.setVisible(true);
    }

    /**
     * @return {@code true} if {@link #tfUsername} and {@link #tfPassword} are not {@code null} and not empty.
     */
    public boolean validUsernameAndPassword() {
        //TODO
//        return (tfUsername.getText() != null && tfPassword.getText() != null) &&
//                !(tfUsername.getText().isEmpty() && tfPassword.getText().isEmpty());
        return true;
    }

    public boolean validFinger() {
        return false;
    }

    public boolean authorized() {
        //TODO: proper login
        return true;
    }

    @Deprecated
    public String getLang() {
        lang = langButt.getText();
        lang = lang.substring(lang.length() - 2);
        return lang;
    }


    public void selectMethod() {
        development.setVisible(false);
        masterLabel.setText(ResourceBundle.getBundle(FXRouter.getCurrentBundleName()).getString("login.select"));
        setMethodsContainersVisible(false);
        containerMethods.setVisible(true);
        back.setVisible(false);
    }

    @FXML
    public void renderTextFields(ActionEvent actionEvent) {
        containerMethods.setVisible(false);
        masterLabel.setText(ResourceBundle.getBundle(FXRouter.getCurrentBundleName()).getString("login.method.password"));
        setMethodsContainersVisible(false);
        containerPassword.setVisible(true);
        back.setVisible(true);
    }

    @FXML
    public void selectToScanFinger(MouseEvent mouseEvent) throws IOException, InterruptedException {
        containerMethods.setVisible(false);
        masterLabel.setText(ResourceBundle.getBundle(FXRouter.getCurrentBundleName()).getString("login.method.fingerprint"));
        setMethodsContainersVisible(false);
        containerFingerprint.setVisible(true);
        back.setVisible(true);
        animateScannerV();
        scanFinger();
    }


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

    public void animateScannerH() {
        animateScanner(scannerC, getHPath(scannerC.getX(), scannerC.getY()));
    }

    public void animateScannerV() {
        animateScanner(scannerF, getVPath(scannerF.getX(), scannerF.getY()));
    }

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

    @FXML
    public void scanFinger() throws IOException, InterruptedException {
        development.setText("Development: Finger scan function is not implemented yet.");
        development.setVisible(true);
        //TODO actually scan a finger and compare it to the db values
        if (validFinger()) authorize();
        else handleAuthorisationError();
    }

    @FXML
    public void scanCard(ActionEvent actionEvent) {
        development.setText("Development: Card scan function is not implemented yet.");
        development.setVisible(true);
        containerMethods.setVisible(false);
        masterLabel.setText(ResourceBundle.getBundle(FXRouter.getCurrentBundleName()).getString("login.method.card"));
        setMethodsContainersVisible(false);
        containerCard.setVisible(true);
        back.setVisible(true);
        animateScannerH();
    }

    @FXML
    public void goBack(ActionEvent actionEvent) {
        selectMethod();
    }

}
