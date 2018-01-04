package models.database;

import com.google.inject.ImplementedBy;
import models.storage.Person;
import play.db.jpa.Transactional;

import java.util.List;
import java.util.concurrent.CompletionStage;

/**
 * This interface provides a non-blocking API for possibly blocking operations.
 */
@ImplementedBy(JPAPersonRepository.class)
public interface PersonRepository {

    CompletionStage<Person> add(Person person);

    @Transactional
    List<Person> list();

    void delete(Long id);

    void editPerson(Long id,String name);
}
