package models;

import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
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
    @Transactional
    public void delete(Long id) {
        jpaApi.withTransaction(() -> {
            EntityManager em = jpaApi.em();
            Person person = em.find(Person.class, id);
            em.getTransaction().begin();
            em.remove(person);
            em.getTransaction().commit();
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
        em.persist(person);
        return person;
    }
}
