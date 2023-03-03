package com.learning.computerDatabase;

import com.learning.computerDatabase.actions.CrocodileDatabase;
import io.gatling.javaapi.core.FeederBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Session;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.util.Date;
import java.util.Random;
import java.util.function.Function;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class crocodilePractise extends Simulation {
    FeederBuilder<String> feederBuilder = csv("users.csv").circular();
    Function<Session, String> crocName = session -> "user_" + String.valueOf(new Random().nextInt(100));
    Function<Session, String> date = session ->  String.valueOf(new Date());

    HttpProtocolBuilder httpProtocolBuilder = http.baseUrl("https://test-api.k6.io")
            .acceptHeader("application/json");

    private ScenarioBuilder scn = scenario("Creating users")
            .feed(feederBuilder)
            .exec(CrocodileDatabase.createUser)
//          .exec(session -> {
//              Session newSession = session.set("body","#{responseBody}");
//              System.out.println(newSession);
//              return newSession;
//          })
            ;

    private ScenarioBuilder scn2 = scenario("Login as user #{username}")
            .feed(feederBuilder)
            .exec(CrocodileDatabase.loginUser)
            .exec(session -> {
                Session newSession = session.set("body","#{loginResponse}");
                System.out.println(newSession);
                return newSession;
            })
            .pause(10)
            .exec(CrocodileDatabase.getCroc)
//            .exec(session -> {
//                Session newSession = session.set("body","#{bodyS}");
//                System.out.println(newSession);
//                return newSession;
//            })
            .pause(10)
            .repeat(2,"counter")
            .on(exec(CrocodileDatabase.addCroc))
//            .exec(session -> {
//                Session newSession = session.set("body","#{addCroc}");
//                System.out.println(newSession);
//                return newSession;
//            })
            .pause(10)
            .exec(CrocodileDatabase.getCroc)
            .pause(10)
            .exec(CrocodileDatabase.getSpecificCroc)
            .pause(10)
            .exec(CrocodileDatabase.logoutUser)
            .pause(10);

    {
        setUp(scn2.injectOpen(atOnceUsers(1))).protocols(httpProtocolBuilder);
    }
}
