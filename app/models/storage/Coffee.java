package models.storage;

import javax.persistence.*;

@Entity
@Table (name = "coffee")
public class Coffee {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long id;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
