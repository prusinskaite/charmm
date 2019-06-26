package start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import support.FXRouter;

import java.util.ResourceBundle;

public class Main extends Application {
    ResourceBundle bundle;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXRouter.bind(this, primaryStage);
        FXRouter.when("login", "/fxml/login.fxml", "CHARMM - Login", new Double(1000), new Double(700));
        FXRouter.when("home", "/fxml/home.fxml", "CHARMM - Home", new Double(1000), new Double(700));
        FXRouter.when("logbook", "/fxml/logbook.fxml", "CHARMM - Logbook", new Double(1000), new Double(700));
        FXRouter.when("graphs", "/fxml/graphs.fxml", "CHARMM - Graphs",new Double(1000), new Double(700));
        FXRouter.when("administer", "/fxml/administer.fxml", "CHARMM - Administer",new Double(1000), new Double(700));
          bundle = ResourceBundle.getBundle("locales.messages");

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"), bundle);
        primaryStage.setTitle("CHARMM");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
