package vezh_bank.persistence.entity;

import core.enums.EventType;

import javax.persistence.*;
import java.util.Date;

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
    private Date date;

    public Event() {
    }

    public Event(EventType type, String data, Date date) {
        this.type = type.toString();
        this.data = data;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    // TODO: return EventType
    public String getType() {
        return type;
    }

    public String getData() {
        return data;
    }

    public Date getDate() {
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
