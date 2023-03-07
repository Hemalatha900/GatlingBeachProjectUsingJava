package com.learning.computerDatabase.session;
import com.learning.computerDatabase.actions.ComputerDatabase;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
//import io.gatling.javaapi.http.HttpProtocolBuilder;

public class PTest1 extends Simulation {

    //http protocol
    HttpProtocolBuilder httpProtocolBuilder = http.baseUrl("https://reqres.in/");


    //Scenario
    ScenarioBuilder scenarioBuilder= scenario("Get List of users")
            .exec(ComputerDatabase.getListofUsers);

// get total emp for this month : this api
// get absent emp for the month


    {
        setUp(
                scenarioBuilder.injectOpen(atOnceUsers(1))
        ).protocols(httpProtocolBuilder);
    }






}