package entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

public class Administration extends BaseEntity {

    private Medicine medicine;
    private Double concentration;
    private Patient patient;
    private List<Record> records = new ArrayList<>();

    public Administration(long id, Medicine medicine, Double concentration, Patient patient) {
        super(id);
        this.medicine = medicine;
        this.concentration = concentration;
        this.patient = patient;
    }

    public Record getLatestRecord() {
        records.stream().sorted(Record::compareTo);
        return records.get(records.size() - 1);
    }

    public StringProperty lastRecordProperty() {
        return new SimpleStringProperty(String.valueOf(getLatestRecord().getDosage()));
    }

    public void addRecord(Record record) {
        records.add(record);
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public Double getConcentration() {
        return concentration;
    }

    public void setConcentration(Double concentration) {
        this.concentration = concentration;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }
}
