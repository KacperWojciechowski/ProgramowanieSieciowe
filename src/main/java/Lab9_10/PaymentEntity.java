package Lab9_10;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class PaymentEntity {
    static int ID = 0;

    int idpayment;
    float bonus;
    Date dateOfPayment;
    int iduser;
    float pay;

    public PaymentEntity()
    {
        idpayment = ID++;
    }

    @ManyToOne
    private UsersEntity user;

}
