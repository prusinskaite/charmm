package entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Date;

public class Record extends BaseEntity implements Comparable{
    private Date time;
    private Employee prescriber;
    private Double dosage;

    public Record(Date time, Employee prescriber, Double dosage) {
        //TODO generated ids
        super(1);
        this.time = time;
        this.prescriber = prescriber;
        this.dosage = dosage;
    }


    public StringProperty dosageProperty() {
        return new SimpleStringProperty(String.valueOf(dosage));
    }

    public String getTimeAsString() {
        return TIME_FORMAT.format(time);
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Employee getPrescriber() {
        return prescriber;
    }

    public void setPrescriber(Employee prescriber) {
        this.prescriber = prescriber;
    }

    public Double getDosage() {
        return dosage;
    }

    public void setDosage(Double dosage) {
        this.dosage = dosage;
    }

    @Override
    public int compareTo(Object o) {
        return time.compareTo(((Record) o).getTime());
    }
}
