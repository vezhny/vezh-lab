package vezh_bank.persistence.entity;

import core.enums.Role;

import javax.persistence.*;

@Entity
@Table(name = "USER_ROLES")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_ID")
    private int id;

    @Column(name = "ROLE_NAME")
    private String name;

    public UserRole() {
    }

    public UserRole(Role role) {
        this.name = role.toString();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
