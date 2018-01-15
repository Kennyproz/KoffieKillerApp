package models.database.Interfaces;

import models.storage.Party;

import java.util.concurrent.CompletionStage;

public interface GroupRepository {

    CompletionStage<Party> add(Party group);
}
