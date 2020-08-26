FROM tomcat:8
RUN curl –u admin:admin123 http://52.40.200.188:8082/artifactory/GOL/gameoflife.war
RUN cp gameoflife.war /usr/local/tomcat/webapps
EXPOSE 8080
CMD [“catalina.sh”, “run”]
