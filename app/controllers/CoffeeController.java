package controllers;

import models.database.Interfaces.CoffeeRepository;
import models.storage.Coffee;
import play.libs.concurrent.HttpExecutionContext;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletionStage;

public class CoffeeController extends Controller {
    private final FormFactory formFactory;
    private final CoffeeRepository coffeeRepository;
    private final HttpExecutionContext ec;

    @Inject
    public CoffeeController(FormFactory formFactory, CoffeeRepository coffeeRepository, HttpExecutionContext ec) {
        this.formFactory = formFactory;
        this.coffeeRepository = coffeeRepository;
        this.ec = ec;
    }

    public Result index() {
        return ok(views.html.site.index.render());
    }

    public CompletionStage<Result> addCoffee() {
        Coffee coffee = formFactory.form(Coffee.class).bindFromRequest().get();
        return coffeeRepository.add(coffee).thenApplyAsync(p -> redirect(routes.CoffeeController.index()), ec.current());
    }

    public Result addCoffeeView() {
        return ok(views.html.coffee.addCoffee.render()).as("text/html");
    }

    public Result editCoffee(Long id,String name){
        coffeeRepository.editCoffee(id, name);
        return getCoffees();
    }

    public Result deleteCoffee(Long id){
        coffeeRepository.delete(id);
        return ok();
    }
    public Result getCoffees() {
        List<Coffee> result = coffeeRepository.list();
        return ok(views.html.coffee.coffees.render(result)).as("text/html");
    }
}
