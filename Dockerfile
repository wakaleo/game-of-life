FROM tomcat
MAINTAINER sankar
ARG CONT_IMG_VER
WORKDIR /usr/local/tomcat
EXPOSE 8080
ADD  http://13.59.210.12:8081/nexus/content/repositories/releases/com/wakaleo/gameoflife/gameoflife/${CONT_IMG_VER}/gameoflife-${CONT_IMG_VER}.war /usr/local/tomcat/webapps
