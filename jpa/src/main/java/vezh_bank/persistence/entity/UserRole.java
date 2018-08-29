package vezh_bank.persistence.entity;

import vezh_bank.enums.Role;

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

//    @OneToMany(mappedBy = "role")
//    private List<User> users;

    public UserRole() {
    }

    public UserRole(Role role) {
        this.name = role.toString();
    }

    public int getId() {
        return id;
    }

    // TODO: return Role
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
