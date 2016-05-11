package controllers;

import core.DataService;
import model.Tram;
import play.*;
import play.libs.Json;
import play.mvc.*;

import views.html.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class Application extends Controller {


    private final DataService data = new DataService();

    public Result index(){
        return ok(index.render("Your new application is ready."));
    }

    public Result data() throws IOException, ParseException {
        List<Tram> d = data.getData();

        return ok(Json.toJson(d));
    }
}
