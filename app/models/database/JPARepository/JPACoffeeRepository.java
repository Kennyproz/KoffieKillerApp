package models.database.JPARepository;


import models.database.DatabaseExecutionContext;
import models.database.Interfaces.CoffeeRepository;
import models.storage.Coffee;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class JPACoffeeRepository implements CoffeeRepository {

    private final JPAApi jpaApi;
    private final DatabaseExecutionContext executionContext;

    @Inject
    public JPACoffeeRepository(JPAApi jpaApi, DatabaseExecutionContext executionContext) {
        this.jpaApi = jpaApi;
        this.executionContext = executionContext;
    }

    @Override
    public CompletionStage<Coffee> add(Coffee coffee) {
        return supplyAsync(() -> wrap(em -> insert(em, coffee)), executionContext);
    }

    @Override
    public List<Coffee> list() {
        return jpaApi.withTransaction(() -> {
            EntityManager em = jpaApi.em();
            return em.createQuery("select c from Coffee c", Coffee.class).getResultList();
        });
    }

    @Override
    public void delete(Long id) {
        jpaApi.withTransaction(() -> {
            EntityManager em = jpaApi.em();
            Coffee coffee = em.find(Coffee.class, id);
            em.remove(coffee);
        });
    }

    @Override
    public void editCoffee(Long id, String name) {
        jpaApi.withTransaction(() -> {
            EntityManager em = jpaApi.em();
            Coffee coffee = em.find(Coffee.class,id);
            if(!name.isEmpty()){
                coffee.setName(name);
            }
        });
    }
    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

    private Coffee insert(EntityManager em, Coffee coffee) {
        em.persist(coffee);
        return coffee;
    }
}
