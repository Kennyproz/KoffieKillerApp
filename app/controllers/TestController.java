package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.database.Interfaces.PersonRepository;
import models.storage.CoffeeEncryptor;
import models.storage.Person;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class TestController extends Controller {

    private final PersonRepository personRepository;
    private final HttpExecutionContext ec;


    @Inject
    public TestController(PersonRepository personRepository, HttpExecutionContext ec) {
        this.personRepository = personRepository;
        this.ec = ec;
    }

    public Result getTestPage() {
        return ok(views.html.testing.test.render()).as("text/html");
    }

    public Result getTestPage2() {
        return ok(views.html.testing.test2.render()).as("text/html");
    }

    public Result TestPersons() {
        List<Person> result = personRepository.list();
        String key = "XMzDdG4D03CKm2IxIWQw7g==";
        String test = "IWantToBeEncrypted";


        try {

            String encrypted = CoffeeEncryptor.symmetricEncrypt(test, key);
            JsonNode node = Json.toJson(encrypted);
            return ok(node);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
