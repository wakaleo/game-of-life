FROM tomcat:8-jre8


ADD /usr/share/tomcat7/.jenkins/workspace/dockerdeploy/gameoflife-web/target/gameoflife.war /usr/local/tomcat/webapps/

EXPOSE 8080
CMD ["catalina.sh", "run"]
