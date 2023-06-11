package Lab9_10;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Getter
@Setter
public class DepartmentsEntity {
    static int ID=0;

    int iddepartment;
    String address;
    String departmentname;
    String description;
    String mainmail;
    String mainphone;
    String mainwww;
    public DepartmentsEntity()
    {
        iddepartment = ID++;
    }

    @ManyToMany(targetEntity = UsersEntity.class, mappedBy = "departments", fetch = FetchType.EAGER)
    private List<UsersEntity> users;
}
