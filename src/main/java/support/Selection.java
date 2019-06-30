package support;

import entities.Employee;
import entities.Patient;

import java.util.ResourceBundle;

/**
 * This class holds the selected data.
 */
public class Selection {

    private static Patient selectedPatient;

    private static Employee currentUser;

    private static ResourceBundle language;

    private static String currentLang;

    public static Patient getSelectedPatient() {
        return selectedPatient;
    }

    public static void setSelectedPatient(Patient selectedPatient) {
        Selection.selectedPatient = selectedPatient;
    }

    public static Employee getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(Employee currentUser) {
        Selection.currentUser = currentUser;
    }

    public static ResourceBundle getLanguage() {
        return language;
    }

    public static void setLanguage(ResourceBundle language) {
        Selection.language = language;
    }

    public static void setLanguage(String language) {
        currentLang = language;
        Selection.language = ResourceBundle.getBundle("locales.messages" + (language.equalsIgnoreCase("en") ? "" : "_nl"));
    }

    public static String getCurrentLanguage() {
        return currentLang;
    }
}
