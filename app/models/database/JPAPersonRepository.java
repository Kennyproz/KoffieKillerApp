package models.database;

import models.storage.Person;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;


import static java.util.concurrent.CompletableFuture.supplyAsync;

/**
 * Provide JPA operations running inside of a thread pool sized to the connection pool
 */
public class JPAPersonRepository implements PersonRepository {

    private final JPAApi jpaApi;
    private final DatabaseExecutionContext executionContext;

    @Inject
    public JPAPersonRepository(JPAApi jpaApi, DatabaseExecutionContext executionContext) {
        this.jpaApi = jpaApi;
        this.executionContext = executionContext;
    }

    @Override
    public CompletionStage<Person> add(Person person) {
        return supplyAsync(() -> wrap(em -> insert(em, person)), executionContext);
    }

    @Override
    public void editPerson(Long id, String name){
        jpaApi.withTransaction(() -> {
            EntityManager em = jpaApi.em();
            Person person = em.find(Person.class,id);
            if(!name.isEmpty()){
                person.setName(name);
            }
        });
    }

    @Override
    public boolean login(String username, char[] password) {
        return jpaApi.withTransaction(() -> {
            EntityManager em = jpaApi.em();
            return login(em, username, password);
        });
    }

    @Override
    @Transactional
    public void delete(Long id) {
        jpaApi.withTransaction(() -> {
            EntityManager em = jpaApi.em();
            Person person = em.find(Person.class, id);
            em.remove(person);
        });
    }

    @Override
    @Transactional
    public List<Person> list() {
        return jpaApi.withTransaction(() -> {
            EntityManager em = jpaApi.em();
            return em.createQuery("select p from Person p", Person.class).getResultList();
        });
    }

    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

    private Person insert(EntityManager em, Person person) {

        person.setSalt(getNextSalt());
        person.setHashedPassword(hash(person.getPassword(), person.getSalt(), 1000, 256));
        String fakePw = "000000000";
        person.setPassword(fakePw.toCharArray());

        em.persist(person);
        return person;
    }

    private boolean login(EntityManager em, String username, char[] password) {
        TypedQuery<Person> query = em.createQuery("select p from Person p where username = :username", Person.class);
        Person personToLogin = query.setParameter("username", username).getSingleResult();

        byte[] passwordToCheck = hash(password, personToLogin.getSalt(), 1000, 265);

        return Arrays.equals(passwordToCheck, personToLogin.getHashedPassword());
    }

    private byte[] getNextSalt() {
        byte[] salt = new byte[128];
        Random random = new SecureRandom();
        random.nextBytes(salt);
        return salt;
    }

    private static byte[] hash(char[] password, byte[] salt, int iterations, int key) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, key);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            spec.clearPassword();
        }
    }
}
