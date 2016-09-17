FROM selenium/standalone-firefox:latest

ENV MAVEN_VERSION 3.3.3
ENV DISPLAY :99

USER root

RUN apt-get update -qqy \
  && apt-get install -y openjdk-8-jdk && \
  rm -rf /var/lib/apt/lists/*

RUN wget -O- http://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz | tar xzf - -C /opt \
  && mv /opt/apache-maven-$MAVEN_VERSION /opt/maven \
  && ln -s /opt/maven/bin/mvn /usr/bin/mvn

RUN mkdir /home/seluser/gameoflife-acceptance-tests \
  && mkdir /home/seluser/gameoflife-web
COPY /gameoflife-acceptance-tests /home/seluser/gameoflife-acceptance-tests
COPY /gameoflife-web /home/seluser/gameoflife-web
RUN chown -R seluser /home/seluser/gameoflife-acceptance-tests \
  && chmod -R u+rX /home/seluser/gameoflife-acceptance-tests \
  && chown -R seluser /home/seluser/gameoflife-web \
  && chmod -R u+rX /home/seluser/gameoflife-web

USER seluser

ENV MAVEN_HOME /opt/maven

EXPOSE 9090

CMD ["mvn"]
