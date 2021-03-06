package models.storage;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "person")
public class Person implements Serializable{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long id;

    public String username;
    public char[] password;

    private byte[] salt;
    private byte[] hashedPassword;

    @ManyToMany
    private List<Party> groups;

    public Person() {}

    public Person(String username, char[] password) {
        this.username = username;
        this.password = password;
        groups = new ArrayList<>();
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public List<Party> getGroups() {
        return groups;
    }

    public void setGroups(List<Party> groups) {
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

    public Long getId() {
        return id;
    }
}
