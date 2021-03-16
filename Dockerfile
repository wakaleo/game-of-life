FROM tomcat
WORKDIR /var/lib/jenkins/workspace/game/gameoflife-web/target
COPY gameoflife.war /usr/local/tomcat/webapps
EXPOSE 8080
CMD ["/usr/local/tomcat/bin/catalia.sh","run"]
