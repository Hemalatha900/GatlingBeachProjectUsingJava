package com.learning.computerDatabase;

import static io.gatling.javaapi.core.CoreDsl.*;

import com.learning.computerDatabase.actions.ComputerDatabase;
import com.learning.computerDatabase.common.HttpProtocol;
import io.gatling.javaapi.core.*;

public class SearchForComputer extends Simulation {

    private ScenarioBuilder scn = scenario("SearchForComputer")
            .exec(ComputerDatabase.home_Page)
            .pause(4)
            .exec(ComputerDatabase.page1)
            .pause(3)
            .exec(ComputerDatabase.page2)
            .pause(7)
            .exec(ComputerDatabase.search)
            .pause(2)
            .exec(ComputerDatabase.viewDetails);

    {
        setUp(scn.injectOpen(atOnceUsers(1))).protocols(HttpProtocol.httpProtocol);
    }
}

