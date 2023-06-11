import Lab9_10.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class lab910Test {
    @Autowired
    private DepartmentsRepository departmentsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @BeforeEach
    void beforeEach()
    {
        UsersEntity u1 = new UsersEntity();
        u1.setFirstname("Kacper");
        u1.setLastname("Wojciechowski");
        u1.setUsername("Gerwant");
        u1.setDescription("Some random desc");
        u1.setPassword("TestPassword");

        UsersEntity u2 = new UsersEntity();
        u2.setFirstname("Mariusz");
        u2.setLastname("Jedlikowski");
        u2.setUsername("Mario");
        u2.setDescription("Other random desc");
        u2.setPassword("Other test password");

        DepartmentsEntity d1 = new DepartmentsEntity();
        d1.setDepartmentname("ISSI");
        d1.setAddress("A2");
        d1.setMainphone("123456789");
        d1.setMainmail("issi@uz.zgora.pl");
        d1.setMainwww("www.issi.uz.zgora.pl");

        List<UsersEntity> d1Users = new ArrayList<>();
        d1Users.add(u1);
        d1Users.add(u2);
        d1.setUsers(d1Users);

        DepartmentsEntity d2 = new DepartmentsEntity();
        d2.setDepartmentname("IMEI");
        d2.setAddress("A2");
        d2.setMainphone("123456789");
        d2.setMainmail("imei@uz.zgora.pl");
        d2.setMainwww("www.imei.uz.zgora.pl");

        List<UsersEntity> d2Users = new ArrayList<>();
        d2Users.add(u2);
        d2.setUsers(d2Users);

        DepartmentsEntity d3 = new DepartmentsEntity();
        d3.setDepartmentname("WIEA");
        d3.setAddress("A2");
        d3.setMainphone("123456789");
        d3.setMainmail("wiea@uz.zgora.pl");
        d3.setMainwww("www.wiea.uz.zgora.pl");

        d3.setUsers(new ArrayList<>());

        u1.getDepartments().add(d1);
        u2.getDepartments().add(d1);
        u2.getDepartments().add(d2);

        PaymentEntity p1 = new PaymentEntity();
        p1.setUser(u1);
        p1.setIduser(u1.getIduser());
        p1.setPay(9001);
        p1.setBonus(150);
        p1.setDateOfPayment(Date.from(Instant.from(LocalDate.now())));

        PaymentEntity p2 = new PaymentEntity();
        p2.setUser(u2);
        p2.setIduser(u2.getIduser());
        p2.setPay(9010);
        p2.setBonus(150);
        p2.setDateOfPayment(Date.from(Instant.from(LocalDate.now())));

        departmentsRepository.save(d1);
        departmentsRepository.save(d2);
        departmentsRepository.save(d3);
        userRepository.save(u1);
        userRepository.save(u2);
        paymentRepository.save(p1);
        paymentRepository.save(p2);
    }

    @Test
    void departmentsHaveCorrectEmployeesCount()
    {
        List<Integer> expectedEmployesCount = new ArrayList<>();
        expectedEmployesCount.add(2);
        expectedEmployesCount.add(1);
        expectedEmployesCount.add(0);
        int index = 0;
        for (UsersEntity user : userRepository.findAll())
        {
            Assert.isTrue(
                    user.getDepartments().size() == expectedEmployesCount.get(index++),
                    "Employes count doesn't match");
        }
    }

    @Test
    void test()
    {
        Assert.isTrue(true, "");
    }
}
