FROM tomcat:7-jre7
MAINTAINER jianghaohai@lattebank.com
 
RUN aws s3 cp s3://lattebank-jenkins-deploy-dev/${APP_NAME}/version.txt /tmp/deploy/ --region=cn-north-1 && \
    aws s3 cp s3://lattebank-jenkins-deploy-dev/${APP_NAME}/$(head -n 1 /tmp/deploy/version.txt) /tmp/deploy/ --region=cn-north-1 && \
    mkdir ${CATALINA_HOME}/webapps/${APP_NAME} && \
    unzip -q /tmp/deploy/$(head -n 1 /tmp/deploy/version.txt) -d ${CATALINA_HOME}/webapps/${APP_NAME} && \
    wget http://${PROPLUS_HOST}/proplus/propFiles/${APP_NAME}/spring-config.properties -O ${CATALINA_HOME}/webapps/${APP_NAME}/WEB-INF/classes/spring-config.properties && \
    rm -rf /tmp