package models.storage;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Party {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long id;

    private String name;
    private int peopleLimit;

    @ManyToMany(mappedBy = "groups")
    private List<Person> members;

    public Party() {
        members = new ArrayList<>();
    }

    public Party(String name, int peopleLimit) {
        members = new ArrayList<>();
        this.name = name;
        this.peopleLimit = peopleLimit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPeopleLimit() {
        return peopleLimit;
    }

    public void setPeopleLimit(int peopleLimit) {
        this.peopleLimit = peopleLimit;
    }

    public List<Person> getMembers() {
        return members;
    }

    public void setMembers(List<Person> members) {
        this.members = members;
    }
}
