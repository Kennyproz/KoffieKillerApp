package Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.mock.staticmock.MockStaticEntityMethods;

@Entity
public class User {


    @Id @GeneratedValue(strategy = GenerationType.AUTO) private Long id;

    private String username;

    public User(String username) {
        this.username = username;
    }
}
