package com.example;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import java.util.Date;

public class DemoDelegate2 implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        Date now = new Date();
        execution.setVariable("Variable2", now.toString());
        System.out.println("DemoDelegate2 job start="+now);
    }
}
