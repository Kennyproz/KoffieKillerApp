package models.storage;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Person {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long id;

    public String username;
    public char[] password;

    private byte[] salt;
    private byte[] hashedPassword;

    @ElementCollection(targetClass=Group.class)
    private List<Group> groups;

    public Person() {}

    public Person(String username, char[] password) {
        this.username = username;
        this.password = password;
        groups = new ArrayList<>();
    }

    public void setName(String username) {
        this.username = username;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public byte[] getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(byte[] hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
