package models.database.JPARepository;

import models.database.DatabaseExecutionContext;
import models.database.Interfaces.GroupRepository;
import models.storage.Party;
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
    public CompletionStage<Party> add(Party party) {
        return supplyAsync(() -> wrap(em -> insert(em, party)), executionContext);
    }

    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

    private Party insert(EntityManager em, Party party) {
        em.persist(party);
        return party;
    }
}
