package com.example;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;

import java.util.Locale;

public class DemoActiviti {

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);

        ProcessEngineConfiguration cfg = ProcessEngineConfiguration
                .createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        ProcessEngine processEngine = cfg.buildProcessEngine();

        createIdentity(processEngine, "programmer", "programmers");
        createIdentity(processEngine, "tester", "testers");
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

