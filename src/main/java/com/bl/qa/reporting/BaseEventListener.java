package com.bl.qa.reporting;

import cucumber.api.Plugin;
import cucumber.api.event.EventHandler;
import cucumber.api.event.EventListener;
import cucumber.api.event.EventPublisher;
import cucumber.api.event.TestCaseFinished;
import cucumber.api.event.TestCaseStarted;
import cucumber.api.event.TestRunFinished;
import cucumber.api.event.TestRunStarted;
import cucumber.api.event.TestStepStarted;
import cucumber.api.event.TestStepFinished;


public class BaseEventListener implements EventListener, Plugin {

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestRunStarted.class, runStarted);
        publisher.registerHandlerFor(TestRunFinished.class, runFinished);
        publisher.registerHandlerFor(TestStepStarted.class, stepStarted);
        publisher.registerHandlerFor(TestStepFinished.class, stepFinished);
        publisher.registerHandlerFor(TestCaseStarted.class, caseStarted);
        publisher.registerHandlerFor(TestCaseFinished.class, caseFinished);
    }

    private EventHandler<TestRunStarted> runStarted = event -> {

    };

    private EventHandler<TestRunFinished> runFinished = event -> {

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
