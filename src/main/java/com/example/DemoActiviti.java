package com.example;

import org.activiti.engine.*;
import org.activiti.engine.form.FormData;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class DemoActiviti {

    private static final String DEV_PROCESS = "devProcess";

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
                    .addClasspathResource("processes/MyProcess.bpmn").deploy();
            System.out.println("deploy process success");
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
            System.exit(0);
        }

        processInstance = runtimeService.createProcessInstanceQuery().deploymentId(deployment.getId()).singleResult();

        TaskService taskService = processEngine.getTaskService();
        FormService formService = processEngine.getFormService();

        List<Task> tasks = new ArrayList<>();
        if ("develop".equals(mode)) {
            System.out.println("develop mode");
            // Задачи для разработчика
            tasks = taskService.createTaskQuery().taskCandidateGroup("programmers").list();
            if (tasks.isEmpty()) {
                System.out.println("Задач на разработку нет");
                System.exit(0);
            }
        }

        if ("test".equals(mode)) {
            System.out.println("test mode");
            // Задачи для тестирования
            tasks = taskService.createTaskQuery().taskCandidateGroup("testers").list();
            if (tasks.isEmpty()) {
                System.out.println("Задач на тестирование нет");
                System.exit(0);
            }
        }

        Scanner scanner = new Scanner(System.in);

        if (processInstance != null && !processInstance.isEnded()) {
            System.out.println("tasks count: [" + tasks.size() + "]");

            for (Task task : tasks) {
                System.out.println("Task:" + task.getTaskDefinitionKey() + ", id=" + task.getId());
                FormData formData = formService.getTaskFormData(task.getId());
                Map<String, Object> variables = new HashMap<String, Object>();
                for (FormProperty formProperty : formData.getFormProperties()) {
                    System.out.println("Enter varName <" + formProperty.getName() +">:");
                    String value = scanner.nextLine();
                    variables.put(formProperty.getId(), value);
                }

                taskService.complete(task.getId(), variables);
                System.out.println("Task complete success:" + task.getTaskDefinitionKey());
            }
            // Re-query the process instance, making sure the latest state is available
            //processInstance = runtimeService.createProcessInstanceQuery()
            //        .processInstanceId(processInstance.getId()).singleResult();
        }
    }

    public static void createIdentity(ProcessEngine processEngine, String userName, String userGroup) {
        IdentityService identityService = processEngine.getIdentityService();

        String userId = userName + "Id";
        if (identityService.createUserQuery().userId(userId).count() == 0) {
            User user = identityService.newUser(userName);
            user.setId(userId);
            user.setEmail(userName + "@gmail.com");
            identityService.saveUser(user);

            System.out.println("user created success fully");
        }

        String groupId = userGroup + "Id";
        if (identityService.createGroupQuery().groupId(groupId).count() == 0) {
            Group group = identityService.newGroup(userGroup);
            group.setName(userGroup);
            group.setId(groupId);

            identityService.saveGroup(group);

            System.out.println("group created success fully");
        }

        if (identityService.createGroupQuery().groupId(groupId).list().size() > 0) {
            identityService.createMembership(userId, groupId);
            System.out.println("user to group success fully");
        }
    }
}

