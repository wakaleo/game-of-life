FROM tomcat
MAINTAINER sankar
ARG CONT_IMG_VER
WORKDIR /usr/local/tomcat
COPY tomcat-users.xml /usr/local/tomcat/conf/tomcat-users.xml
EXPOSE 8080
COPY gameoflife.war /usr/local/tomcat/webapps
