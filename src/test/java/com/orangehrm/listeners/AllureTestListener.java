package com.orangehrm.listeners;

import io.qameta.allure.testng.AllureTestNg;
import org.testng.annotations.Listeners;

@Listeners({AllureTestNg.class})
public class AllureTestListener {}
