package com.learning.computerDatabase.actions;

import io.gatling.javaapi.core.ChainBuilder;

import static io.gatling.javaapi.core.CoreDsl.StringBody;
import static io.gatling.javaapi.core.CoreDsl.exec;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class K6TestApis {

    public static ChainBuilder login = exec(http("Login ")
            .post("/auth/cookie/login/").check(status().is(201))
            .header("Content-type", "application/json")
            .formParam("username", "#{username1}")
            .formParam("password", "#{password1}"));

    public static ChainBuilder registerUserChainBuilder = exec(http("Register")
            .post("/user/register/").check(status().is(201))
            .header("Content-Type", "application/json")
            .body(StringBody("{\n" +
                    "    \"username\": \"#{username}\",\n" +
                    "    \"password\": \"#{password}\"\n" +
                    "}")));
    public static ChainBuilder createCrocodile = exec(http("Create Crocodile").post("/my/crocodiles/").check(status().is(201))
            .header("Content-Type", "application/json")
            .formParam("name", "Cr6")
            .formParam("sex", "M")
            .formParam("date_of_birth", "1999-12-31")

    );

    public static ChainBuilder getCrocodiles = exec(http("Get Crocodiles ").get("/my/crocodiles/"));


}
