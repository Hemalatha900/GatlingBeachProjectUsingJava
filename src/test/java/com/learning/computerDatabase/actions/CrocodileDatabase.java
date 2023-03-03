package com.learning.computerDatabase.actions;

import io.gatling.javaapi.core.ActionBuilder;
import io.gatling.javaapi.core.Session;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.function.Function;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class CrocodileDatabase {

    public static ActionBuilder createUser= http("Create user #{username}")
            .post("/user/register/")
            .bodyParts(
                    StringBodyPart("username", "#{username}"),
                    StringBodyPart("first_name", "#{first_name}"),
                    StringBodyPart("last_name", "#{last_name}"),
                    StringBodyPart("email", "#{email}"),
                    StringBodyPart("password", "#{password}")
            )
            .check(bodyString().saveAs("responseBody"))
            ;

    public static ActionBuilder loginUser= http("Login user #{username}")
            .post("/auth/token/login/")
            .bodyParts(
                    StringBodyPart("username", "#{username}"),
                    StringBodyPart("password", "#{password}")
            )
            .check(bodyString().saveAs("loginResponse"))
            ;

    public static ActionBuilder getCroc= http("Get crocodiles")
            .get("/my/crocodiles/")
            .basicAuth("#{username}","#{password}")
            .check(status().is(200))
            .check(bodyString().saveAs("bodyS"))
            .check(substring("user").exists())
            ;

    public static ActionBuilder addCroc= http("Add crocodile #{counter}")
            .post("/my/crocodiles/")
            .basicAuth("#{username}","#{password}")
            .bodyParts(
                    StringBodyPart("name", "user" + String.valueOf(new Random().nextInt(100))),
                    StringBodyPart("sex", "M"),
                    StringBodyPart("date_of_birth", DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH).format(LocalDateTime.now()))
            )
            .check(bodyString().saveAs("addCroc"))
            .check(jsonPath("$.name").saveAs("crocName"))
            .check(jsonPath("$.id").saveAs("crocId"))
            ;

    public static ActionBuilder getSpecificCroc= http("Get crocodile")
            .get("/my/crocodiles/#{crocId}")
            .basicAuth("#{username}","#{password}")
            .check(status().is(200))
            .check(bodyString().saveAs("bodyS"))
            .check(substring("#{crocName}").exists())
            ;

    public static ActionBuilder logoutUser= http("Logout user #{username}")
            .post("/auth/cookie/logout/")
            .check(status().is(200))
            ;

}
