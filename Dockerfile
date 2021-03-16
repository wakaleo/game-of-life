FROM tomcat
#ENV WAR_SRC_PATH=/var/lib/jenkins/workspace/game/gameoflife-web/target/
ENV WAR_DST_PATH=/usr/local/tomcat/webapps/
#WORKDIR ${WAR_SRC_PATH}
#RUN cp gameoflife.war /usr/share/gameoflife.war
ADD gameoflife.war ${WAR_DST_PATH}/gameoflife.war
EXPOSE 8080
CMD ["/usr/local/tomcat/bin/catalia.sh","run"]
