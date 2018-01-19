package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.database.Interfaces.MessageRepository;
import models.storage.CoffeeEncryptor;
import models.storage.Person;
import models.database.Interfaces.PersonRepository;
import models.storage.PrivateMessage;
import play.data.DynamicForm;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionStage;

/**
 * The controller keeps all database operations behind the repository, and uses
 * {@link play.libs.concurrent.HttpExecutionContext} to provide access to the
 * {@link play.mvc.Http.Context} methods like {@code request()} and {@code flash()}.
 */
public class PersonController extends Controller {

    private final Form<PrivateMessage> form;
    private final FormFactory formFactory;
    private final PersonRepository personRepository;
    private final HttpExecutionContext ec;
    private final MessageRepository messageRepository;
    private DynamicForm messageForm;

    @Inject
    public PersonController(FormFactory formFactory, PersonRepository personRepository, HttpExecutionContext ec, MessageRepository messageRepository) {
        this.formFactory = formFactory;
        this.form = formFactory.form(PrivateMessage.class);
        this.personRepository = personRepository;
        this.messageRepository = messageRepository;
        this.ec = ec;
        messageForm = formFactory.form();
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
            System.out.println("Login successful");
            ctx().session().put("user", tempPerson.username);
        } else {
            System.out.println("The thing went skraaaaa, you fucked up");
        }

        return ok();
    }

    public Result chat(){
        List<Person> result = personRepository.list();
        PrivateMessage message = new PrivateMessage();
        Map<String, String> personMap = mapPerson(result);
        return ok(views.html.message.chat.render(result,message,form,personMap));
    }

    private Map<String, String> mapPerson(List<Person> persons){

        Map<String,String> personMap = new HashMap<>();
        for(Person p : persons){
            personMap.put(p.getId().toString(),p.getUsername());
        }
        return personMap;

    }


    public Result sendMsg()  {
        messageForm = formFactory.form().bindFromRequest();
        String sender1d = messageForm.get("sender");
        String recipientId = messageForm.get("recipient");
        String message = messageForm.get("message");

        Person sender = personRepository.getPersonById( Long.parseLong(sender1d));
        Person reciever = personRepository.getPersonById(Long.parseLong(recipientId));

        message = CoffeeEncryptor.symmetricEncrypt(message, "XMzDdG4D03CKm2IxIWQw7g==");
        PrivateMessage privateMessage = new PrivateMessage(sender,reciever,message);
        messageRepository.add(privateMessage);
        return ok(views.html.message.mess.render(privateMessage));
    }

    public Result getMessages(long id) {
        Person person = personRepository.getPersonById(id);

        List<PrivateMessage> result = messageRepository.getMessagesForUser(person);

        JsonNode node = Json.toJson(result);

        return ok(node);
    }
}
