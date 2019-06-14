title bulid project
echo 开始构建项目
mvn install
cd gameoflife-web
mvn jetty:run
echo 构建结束
close