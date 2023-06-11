package Lab9_10;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Getter
@Setter
public class UsersEntity {
    static int ID = 0;

    int iduser;
    String description;
    String firstname;
    String lastname;
    String password;
    String username;

    public UsersEntity()
    {
        iduser = ID++;
    }

    @ManyToMany(
            targetEntity = DepartmentsEntity.class,
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "UsersDepartmentsJoin",
            joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="dept_id")
    )
    private List<DepartmentsEntity> departments;
}
