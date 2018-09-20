package core.dto;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import vezh_bank.enums.Role;
import vezh_bank.persistence.entity.UserRole;

public class UserRoleDTO implements BaseDTO<UserRole> {
    @Expose
    private int id;

    @Expose
    private String name;

    public UserRoleDTO(String name) {
        this.name = name;
    }

    public UserRoleDTO(UserRole userRole) {
        this.id = userRole.getId();
        this.name = userRole.getName();
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
        UserRole userRole = new UserRole(Role.valueOf(name));
        return userRole;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
