package entities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class BaseEntity {
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final DateFormat TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private long id;

    public BaseEntity(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
