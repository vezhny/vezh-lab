package core.dto;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import vezh_bank.persistence.entity.UserRole;

public class UserRoleDTO implements BaseDTO<UserRole> {
    @Expose
    private int id;

    @Expose
    private String name;

    private transient UserRole userRole;

    public UserRoleDTO(String name) {
        this.name = name;
    }

    public UserRoleDTO(UserRole userRole) {
        this.id = userRole.getId();
        this.name = userRole.getName();
        this.userRole = userRole;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public UserRole getEntity() {
        return userRole;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
