package support;

import entities.Employee;
import entities.Patient;

/**
 * This class holds the selected {@link Patient}.
 */
public class Selection {

    private static Patient selectedPatient;

    private static Employee currentUser;

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
}
