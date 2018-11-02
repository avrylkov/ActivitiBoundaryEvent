package com.example;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import java.util.Date;

public class DemoDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        try {
            Thread.sleep(3000L);
            Date now = new Date();
            execution.setVariable("Variable", now.toString());
            System.out.println("DemoDelegate job start=" + now);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
