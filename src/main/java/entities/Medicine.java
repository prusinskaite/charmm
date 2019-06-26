package entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Medicine {
    private long id;
    private final StringProperty name;
    private String notes;

    public Medicine(long id, String name) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
    }

    public long getId() {
        return id;
    }


    public StringProperty nameProperty() {
        return name;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
