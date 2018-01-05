package controllers;

import models.storage.Person;
import models.database.Interfaces.PersonRepository;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletionStage;

/**
 * The controller keeps all database operations behind the repository, and uses
 * {@link play.libs.concurrent.HttpExecutionContext} to provide access to the
 * {@link play.mvc.Http.Context} methods like {@code request()} and {@code flash()}.
 */
public class PersonController extends Controller {

    private final FormFactory formFactory;
    private final PersonRepository personRepository;
    private final HttpExecutionContext ec;

    @Inject
    public PersonController(FormFactory formFactory, PersonRepository personRepository, HttpExecutionContext ec) {
        this.formFactory = formFactory;
        this.personRepository = personRepository;
        this.ec = ec;
    }

    public Result index() {
        return ok(views.html.site.index.render());
    }

    public CompletionStage<Result> addPerson() {
        Person person = formFactory.form(Person.class).bindFromRequest().get();
        return personRepository.add(person).thenApplyAsync(p -> redirect(routes.PersonController.index()), ec.current());
    }

    public Result addPersonView() {
        return ok(views.html.person.addPerson.render()).as("text/html");
    }

    public Result getPersons() {
        List<Person> result = personRepository.list();
        return ok(views.html.person.persons.render(result)).as("text/html");
    }

    public Result deletePerson(Long id){
        personRepository.delete(id);
        return ok();
    }

    public Result editPerson(Long id,String name){
        personRepository.editPerson(id, name);
        return getPersons();
    }

    public Result loginView() {
        return ok(views.html.person.login.render()).as("text/html");
    }

    public Result login() {
        Person tempPerson = formFactory.form(Person.class).bindFromRequest().get();
        boolean result = personRepository.login(tempPerson.getUsername(), tempPerson.getPassword());
        if(result) {
            System.out.println("Login succesful");
        } else {
            System.out.println("The thing went skraaaaa, you fucked up");
        }

        return ok();
    }
}
