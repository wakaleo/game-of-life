FROM tomcat
COPY /var/lib/jenkins/workspace/game/gameoflife-web/target/gameoflife.war /usr/local/tomcat/webapps
EXPOSE 8080
CMD ["/usr/local/tomcat/bin/catalia.sh","run"]
