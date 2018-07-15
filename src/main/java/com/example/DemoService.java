package com.example;


import org.activiti.engine.FormService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormData;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


@Service
public class DemoService {

    @Autowired
    private TaskService taskService;
    @Autowired
    private FormService formService;

    public void startTest() {
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("testers").list();
        if (tasks.isEmpty()) {
            System.out.println("Задач на тестирование нет");
            return;
        }
        processTasks(tasks);
    }

    public void startDevelop() {
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("develop").list();
        if (tasks.isEmpty()) {
            System.out.println("Задач на разработку нет");
            return;
        }
        processTasks(tasks);
    }

    private void processTasks(List<Task> tasks) {
        Scanner scanner = new Scanner(System.in);
        for (Task task : tasks) {
            System.out.println("Task:" + task.getTaskDefinitionKey() + ", id=" + task.getId());
            FormData formData = formService.getTaskFormData(task.getId());
            Map<String, Object> variables = new HashMap<String, Object>();
            // переменные задачи
            for (FormProperty formProperty : formData.getFormProperties()) {
                System.out.println("Enter varName <" + formProperty.getName() +">:");
                String value = scanner.nextLine();
                variables.put(formProperty.getId(), value);
            }
            // выполняем задачу
            taskService.complete(task.getId(), variables);
            System.out.println("Task complete success:" + task.getTaskDefinitionKey());
        }
    }
}
