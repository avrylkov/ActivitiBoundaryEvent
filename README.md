# ActivitiBoundaryEvent
Activiti Boundary Event

Собирать maven package

установить в локальный репозиторий ojdbc6.jar

mvn install:install-file -Dfile=G:/Project/ojdbc6.jar -DgroupId=com.oracle -DartifactId=ojdbc6 -Dversion=11.2.0 -Dpackaging=jar

deploy BoundaryEvent.bpmn

java -jar DemoActiviti-1.0-SNAPSHOT-jar-with-dependencies.jar deploy

start bmp

java -jar DemoActiviti-1.0-SNAPSHOT-jar-with-dependencies.jar start

job смотрим здесь

SELECT * FROM act_ru_timer_job;

историю переменных здесь

SELECT * FROM act_hi_varinst;

Если изменили bpmn, то увеличиваем Id process = boundaryEventXX в bpmn
в коде java указываем тот же Id, DEV_PROCESS = boundaryEventXX 
и запускаем 

java -jar DemoActiviti-1.0-SNAPSHOT-jar-with-dependencies.jar deploy
