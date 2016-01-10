FROM java:8

RUN apt-get update
RUN apt-get install -y maven

ADD pom.xml /
ADD src /

RUN ["mvn", "dependency:resolve"]
RUN ["mvn", "verify"]
RUN ["mvn", "package"]

ADD target/*.jar /jar/inco.jar

CMD ["/usr/lib/jvm/java-8-openjdk-amd64/bin/java", "-jar", "jar/inco.jar"]

