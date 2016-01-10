FROM java:8

RUN apt-get update
RUN apt-get install -y maven

WORKDIR /inco

ADD pom.xml /inco/
ADD src /inco/

RUN ["mvn", "dependency:resolve"]
RUN ["mvn", "verify"]
RUN ["mvn", "package"]

CMD ["/usr/lib/jvm/java-8-openjdk-amd64/bin/java", "-jar", "inco/inco-node-0.1.jar"]
