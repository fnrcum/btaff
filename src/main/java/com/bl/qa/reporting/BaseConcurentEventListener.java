package com.bl.qa.reporting;

import com.bl.qa.utils.NetworkUtils;

import java.util.Map;

import cucumber.api.Plugin;
import cucumber.api.event.EventHandler;
import cucumber.api.event.ConcurrentEventListener;
import cucumber.api.event.EventPublisher;
import cucumber.api.event.TestCaseFinished;
import cucumber.api.event.TestCaseStarted;
import cucumber.api.event.TestRunFinished;
import cucumber.api.event.TestRunStarted;
import cucumber.api.event.TestStepStarted;
import cucumber.api.event.TestStepFinished;

public class BaseConcurentEventListener implements ConcurrentEventListener, Plugin {

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestRunStarted.class, setup);
        publisher.registerHandlerFor(TestRunFinished.class, teardown);
        publisher.registerHandlerFor(TestStepStarted.class, stepStarted);
        publisher.registerHandlerFor(TestStepFinished.class, stepFinished);
        publisher.registerHandlerFor(TestCaseStarted.class, caseStarted);
        publisher.registerHandlerFor(TestCaseFinished.class, caseFinished);
    }

    private EventHandler<TestRunStarted> setup = event -> {
        Map info = NetworkUtils.getCurrentMachineInfo();
        System.out.println("Your current IP address : " + info.get("ip"));
        System.out.println("Your current Hostname : " + info.get("hostname"));
    };

    private EventHandler<TestRunFinished> teardown = event -> {

    };

    private EventHandler<TestStepStarted> stepStarted = event -> {

    };

    private EventHandler<TestStepFinished> stepFinished = event -> {

    };

    private EventHandler<TestCaseStarted> caseStarted = event -> {

    };

    private EventHandler<TestCaseFinished> caseFinished = event -> {

    };
}
