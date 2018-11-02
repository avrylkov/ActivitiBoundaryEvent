# ActivitiBoundaryEvent
Activiti Boundary Event

Собирать maven package
deploy BoundaryEvent.bpmn
java -jar DemoActiviti-1.0-SNAPSHOT-jar-with-dependencies.jar deploy

start bmp
java -jar DemoActiviti-1.0-SNAPSHOT-jar-with-dependencies.jar start

job смотрим здесь
SELECT * FROM act_ru_timer_job;

историю переменных здесь
SELECT * FROM act_hi_varinst;
