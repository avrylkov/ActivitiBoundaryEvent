package com.example;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import java.util.Date;

public class DemoDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        Date now = new Date();
        execution.setVariable("issue", now.toString());
        System.out.println("job start="+now);
    }
}
