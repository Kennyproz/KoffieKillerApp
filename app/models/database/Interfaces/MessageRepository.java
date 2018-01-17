package models.database.Interfaces;

import models.storage.PrivateMessage;

import java.util.List;
import java.util.concurrent.CompletionStage;

public interface MessageRepository {

    CompletionStage<PrivateMessage> add(PrivateMessage privateMessage);

    List<PrivateMessage> getMessagesForUser(Long personId);
}
