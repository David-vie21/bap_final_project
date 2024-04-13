FROM openjdk:18-alpine

WORKDIR /app

#COPY target/todo_test-0.0.1-SNAPSHOT.jar app.jar
COPY target/todo_test-0.0.1-SNAPSHOT.jar pom.xml /app/
COPY src/test/scala/YourGatlingSimulation.scala /app/src/test/scala/YourGatlingSimulation.scala 

#COPY target/todo_test-0.0.1-SNAPSHOT.jar pom.xml #src/test/scala/YourGatlingSimulation.scala /app/

RUN apk add --no-cache maven

COPY --from=denvazh/gatling:3.1.2 /opt/gatling/lib /gatling

EXPOSE 8080

#CMD ["java", "-cp", "todo_test-0.0.1-SNAPSHOT.jar", "com.example.todo_test"]

CMD ["java", "-jar", "todo_test-0.0.1-SNAPSHOT.jar"]
#ENTRYPOINT ["mvn", "gatling:test", "-#Dgatling.simulationClass=YourGatlingSimulation"]

