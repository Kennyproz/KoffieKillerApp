package models;

import javax.persistence.*;

@Entity
@Table
public class Person {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long id;

    public String name;
}
