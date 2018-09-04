package vezh_bank.persistence.entity;

import vezh_bank.constants.DatePatterns;
import vezh_bank.enums.EventType;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import static vezh_bank.constants.DatePatterns.DEFAULT_DATE_PATTERN;

@Entity
@Table(name = "EVENTS")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EVENT_ID")
    private int id;

    @Column(name = "EVENT_TYPE")
    private String type;

    @Column(name = "EVENT_DATA")
    private String data;

    @Column(name = "EVENT_DATE")
    private String date;

    public Event() {
    }

    public Event(EventType type, String data) {
        this.type = type.toString();
        this.data = data;
        this.date = new SimpleDateFormat(DEFAULT_DATE_PATTERN).format(new Date());
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getData() {
        return data;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", data='" + data + '\'' +
                ", date=" + date +
                '}';
    }
}
