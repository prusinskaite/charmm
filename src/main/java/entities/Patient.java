package entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Patient extends Person {

    private List<Administration> administrations = new ArrayList<>(10);

    public Patient(String fullName, Date birth, long id) {
        super(fullName, birth, id);
    }

    public Administration getAdministrationByMedicine(Medicine medicine) {
        return administrations.stream().filter(e -> e.getMedicine().equals(medicine)).findFirst().get();
    }

    public void addAdministration(Administration administration) {
        administrations.add(administration);
    }

    public void removeAdministration(Administration administration) {
        administrations.remove(administration);
    }

    public void clearAdministrations(Administration administration) {
        administrations.clear();
    }

    public List<Administration> getAdministrations() {
        return administrations;
    }

    public void setAdministrations(List<Administration> administrations) {
        this.administrations = administrations;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
