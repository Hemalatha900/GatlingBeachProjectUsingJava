package com.learning.computerDatabase.assignment;

import com.learning.computerDatabase.actions.K6TestApis;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.core.*;

import java.time.Duration;
import java.util.Random;

import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.core.CoreDsl.*;


public class RegisterUser extends Simulation {

    public HttpProtocolBuilder protocolBuilder = http.baseUrl("https://test-api.k6.io");
    public FeederBuilder.Batchable<String> users = csv("users.csv").circular();
    public FeederBuilder.Batchable<String> loginCredentials = csv("search.csv").circular();


    ScenarioBuilder createUsers = scenario("Register User")

            .repeat(5)
            .on(feed(users)
                    .exec(session -> {
                        System.out.println("Username :" + session.get("username"));
                        System.out.println("Password :" + session.get("password"));
                        return session;
                    })
                    .exec(K6TestApis.registerUserChainBuilder)
            );
    ScenarioBuilder performAction = scenario("Perform Actions")
            .feed(loginCredentials).exec(session -> {
                System.out.println("Username Login :" + session.get("username1"));
                System.out.println("Password Login :" + session.get("password1"));
                return session;
            })
            .exec(K6TestApis.login)
            .exec(session -> {
                        System.out.println("Extracted value after login: " + session);
                        return session;
                    }
            );

    // .exec(getToken)
    //  .exec(createCrocodile);

    {
        setUp(
              //  createUsers.injectOpen(atOnceUsers(1)),
                performAction.injectOpen(atOnceUsers(1))
        ).protocols(protocolBuilder);

    }


}








