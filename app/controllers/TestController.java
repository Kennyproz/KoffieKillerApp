package controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class TestController extends Controller {

    public Result getTestPage() {
        return ok(views.html.testing.test.render()).as("text/html");
    }

    public Result getTestPage2() {
        return ok(views.html.testing.test2.render()).as("text/html");
    }
}
