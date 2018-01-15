package controllers;

import models.database.Interfaces.CoffeeRepository;
import models.database.Interfaces.GroupRepository;
import models.database.JPARepository.JPAGroupRepository;
import models.storage.Party;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

public class GroupController extends Controller {

    private final FormFactory formFactory;
    private final JPAGroupRepository groupRepository;
    private final HttpExecutionContext ec;

    @Inject
    public GroupController(FormFactory formFactory, JPAGroupRepository groupRepository, HttpExecutionContext ec) {
        this.formFactory = formFactory;
        this.groupRepository = groupRepository;
        this.ec = ec;
    }

    public CompletionStage<Result> add() {
        Party party = formFactory.form(Party.class).bindFromRequest().get();
        return groupRepository.add(party).thenApplyAsync(p -> redirect(routes.CoffeeController.index()), ec.current());

    }

}
