FROM tomcat:7.0


COPY /usr/share/tomcat7/.jenkins/workspace/dockerdeploy/gameoflife-web/target/gameoflife.war /usr/local/tomcat/webapps/

EXPOSE 8080
CMD ["catalina.sh", "run"]
