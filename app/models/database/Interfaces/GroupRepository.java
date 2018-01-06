package models.database.Interfaces;

import com.google.inject.ImplementedBy;
import models.storage.Group;

import java.util.concurrent.CompletionStage;

public interface GroupRepository {

    CompletionStage<Group> add(Group group);
}
