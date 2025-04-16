package com.orangehrm.utils;

import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.classic.spi.ILoggingEvent;
import io.qameta.allure.Allure;

public class AllureLogAppender extends AppenderBase<ILoggingEvent> {

    @Override
    protected void append(ILoggingEvent eventObject) {
        String logMessage = "[" + eventObject.getLevel() + "] " + eventObject.getFormattedMessage();
        System.out.println("🔍 AllureLogAppender hit: " + logMessage); // test this
        Allure.step(logMessage);
    }

}