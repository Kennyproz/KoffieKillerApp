package models.database.Interfaces;

import com.google.inject.ImplementedBy;
import models.database.JPARepository.JPACoffeeRepository;
import models.database.JPARepository.JPAPersonRepository;
import models.storage.Coffee;
import play.db.jpa.Transactional;

import java.util.List;
import java.util.concurrent.CompletionStage;

@ImplementedBy(JPACoffeeRepository.class)
public interface CoffeeRepository {

    CompletionStage<Coffee> add(Coffee coffee);

    @Transactional
    List<Coffee> list();

    void delete(Long id);

    void editCoffee(Long id,String name);

}
