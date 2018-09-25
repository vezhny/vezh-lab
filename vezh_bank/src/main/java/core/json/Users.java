package core.json;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import core.dto.UserDTO;

import java.util.List;

public class Users {
    @Expose
    private List<UserDTO> users;

    public Users(List<UserDTO> users) {
        this.users = users;
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
