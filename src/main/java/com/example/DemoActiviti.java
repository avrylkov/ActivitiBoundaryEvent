package com.example;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Locale;

public class DemoActiviti {

    private static final String DEV_PROCESS = "boundaryEvent10";

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);

        ProcessEngineConfiguration cfg = ProcessEngineConfiguration
                .createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        ProcessEngine processEngine = cfg.buildProcessEngine();

        RepositoryService repositoryService = processEngine.getRepositoryService();

        String mode = StringUtils.EMPTY;
        if (args.length > 0) {
            mode = args[0];
        }
        System.out.println("Processes mode: " + mode);

        Deployment deployment;
        if ("deploy".equals(mode)) {
            deployment = repositoryService.createDeployment()
                    .addClasspathResource("processes/BoundaryEvent.bpmn").deploy();
            System.out.println("deploy process success " + deployment.getId());
            System.exit(0);
        } else {
            List<Deployment> myProcesses = repositoryService.createDeploymentQuery()
                    .processDefinitionKey(DEV_PROCESS).list();
            deployment = myProcesses.get(myProcesses.size()-1);
            System.out.println("get process success:" + deployment.getId());
        }

        //
        RuntimeService runtimeService = processEngine.getRuntimeService();

        ProcessInstance processInstance;
        if ("start".equals(mode)){
            ProcessInstance myProcess = runtimeService.startProcessInstanceByKey(DEV_PROCESS);
            System.out.println("start process success:" + myProcess.getName() +", id="+ myProcess.getId());
        }
    }
}

