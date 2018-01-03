package models.storage;

import javax.persistence.*;

@Entity
@Table
public class Person {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long id;

    public String name;

    public void setName(String name) {
        this.name = name;
    }
}
