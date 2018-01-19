package models.database.Interfaces;

import com.google.inject.ImplementedBy;
import models.database.JPARepository.JPAMessageRepository;
import models.storage.Person;
import models.storage.PrivateMessage;

import java.util.List;
import java.util.concurrent.CompletionStage;

@ImplementedBy(JPAMessageRepository.class)
public interface MessageRepository {

    CompletionStage<PrivateMessage> add(PrivateMessage privateMessage);

    List<PrivateMessage> getMessagesForUser(Person person);
}
