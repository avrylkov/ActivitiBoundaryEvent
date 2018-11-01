package com.example;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import java.util.Date;

public class DemoDelegate3 implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        Date now = new Date();
        execution.setVariable("Variable3", now.toString());
        System.out.println("DemoDelegate3 job start="+now);
    }
}
