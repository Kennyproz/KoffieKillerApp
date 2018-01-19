package models.database.JPARepository;

import models.database.DatabaseExecutionContext;
import models.database.Interfaces.MessageRepository;
import models.storage.Person;
import models.storage.PrivateMessage;
import play.db.jpa.JPAApi;
import play.db.jpa.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class JPAMessageRepository implements MessageRepository {

    private final JPAApi jpaApi;
    private final DatabaseExecutionContext executionContext;

    @Inject
    public JPAMessageRepository(JPAApi jpaApi, DatabaseExecutionContext executionContext) {
        this.jpaApi = jpaApi;
        this.executionContext = executionContext;
    }

    @Override
    public CompletionStage<PrivateMessage> add(PrivateMessage privateMessage) {
        return supplyAsync(() -> wrap(em -> insert(em, privateMessage)), executionContext);
    }

    @Override
    @Transactional
    public List<PrivateMessage> getMessagesForUser(Person person) {
            return jpaApi.withTransaction(() -> {
                EntityManager em = jpaApi.em();
                TypedQuery<PrivateMessage> query = em.createQuery("select m from PrivateMessage m where m.recipient = :recipient", PrivateMessage.class);
                return query.setParameter("recipient", person).getResultList();
            });
    }

    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

    private PrivateMessage insert(EntityManager em, PrivateMessage privateMessage) {
        em.persist(privateMessage);
        return privateMessage;
    }
}
