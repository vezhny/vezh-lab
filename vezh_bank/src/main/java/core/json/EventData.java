package core.json;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

public class EventData {

    @Expose
    private String description;

    public EventData() {
    }

    public EventData(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
