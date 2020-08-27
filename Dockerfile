FROM tomcat:8
RUN wget --user=admin --password='admin123'  http://18.237.61.133:8082/artifactory/gol/gameoflife.war
RUN cp gameoflife.war /usr/local/tomcat/webapps
EXPOSE 8080
CMD [“catalina.sh”, “run”]
