package entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Employee extends Person {
    private List<Administration> administrations = new ArrayList<>();

    public Employee(String fullName, Date birth, long id) {
        super(fullName, birth, id);
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
