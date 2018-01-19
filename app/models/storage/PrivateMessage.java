package models.storage;

import models.database.Interfaces.MessageRepository;

import javax.persistence.*;

@Entity
@Table(name = "Messages")
public class PrivateMessage {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public long id;

    // Something, something proof of concept.
    // We know it's not secure, it's for demonstrating purposes only.
    private String encryptKey;

    private String encryptedMessage;

    @OneToOne
    private Person recipient;

    @OneToOne
    private Person sender;


    public PrivateMessage() {
    }

    public PrivateMessage(Person sender, Person recipient, String message){
        this.sender = sender;
        this.recipient = recipient;
        this.encryptedMessage = message;
        this.encryptKey = "XMzDdG4D03CKm2IxIWQw7g==";

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEncryptedMessage() {
        return encryptedMessage;
    }

    public void setEncryptedMessage(String message) {
        this.encryptedMessage = message;
    }

    public Person getRecipient() {
        return recipient;
    }

    public void setRecipient(Person receipent) {
        this.recipient = receipent;
    }

    public Person getSender() {
        return sender;
    }

    public void setSender(Person sender) {
        this.sender = sender;
    }

    public String getEncryptKey() {
        return encryptKey;
    }

    public void setEncryptKey(String key) {
        this.encryptKey = key;
    }
}
