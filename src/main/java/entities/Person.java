package entities;

import java.util.Date;

public class Person extends BaseEntity {
    private String fullName;
    private Date birth;

    public Person(String fullName, Date birth, long id) {
        super(id);
        this.fullName = fullName;
        this.birth = birth;
    }

    public String getBirthAsString() {
        return DATE_FORMAT.format(birth);
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    @Override
    public String toString() {
        return fullName +
                ", " + getBirthAsString() +
                ", " + getId();
    }
}
