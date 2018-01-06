package models.database.JPARepository;

import models.database.DatabaseExecutionContext;
import models.database.Interfaces.GroupRepository;
import models.storage.Coffee;
import models.storage.Group;
import play.db.jpa.JPAApi;

import javax.persistence.EntityManager;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class JPAGroupRepository implements GroupRepository {

    private final JPAApi jpaApi;
    private final DatabaseExecutionContext executionContext;

    public JPAGroupRepository(JPAApi jpaApi, DatabaseExecutionContext executionContext) {
        this.jpaApi = jpaApi;
        this.executionContext = executionContext;
    }

    @Override
    public CompletionStage<Group> add(Group group) {
        return supplyAsync(() -> wrap(em -> insert(em, group)), executionContext);
    }

    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

    private Group insert(EntityManager em, Group group) {
        em.persist(group);
        return group;
    }
}
