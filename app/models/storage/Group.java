package models.storage;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Group {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long id;

    private String name;
    private char[] password;
    private int peopleLimit;

    @ElementCollection(targetClass=Person.class)
    @ManyToMany(mappedBy="groups")
    private List<Person> members;

    public Group(String name, char[] password, int peopleLimit) {
        this.name = name;
        this.password = password;
        this.peopleLimit = peopleLimit;
        members = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
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
