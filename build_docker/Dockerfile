# java 版本
FROM java:8
# 挂载的docker卷
# VOLUME /tmp
# 前者是要操作的jar包  后者自定义jar包名
ADD sentinel-dashboard-1.7.0.jar sentinel-dashboard.jar
# 定义时区参数
ENV TZ=Asia/Shanghai
# 设置时区
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo '$TZ' > /etc/timezone
# 配置启动命令,-D表示设置JVM参数  （xxx.xxx.xxx.xxx换成自己服务器的ip）
ENTRYPOINT ["java","-jar","-Dserver.port=8858","-Dcsp.sentinel.dashboard.server=127.0.0.1:8858","-Dproject.name=sentinel-dashboard","-Dsentinel.dashboard.auth.username=sentinel","-Dsentinel.dashboard.auth.password=sentinel","/sentinel-dashboard.jar"]
