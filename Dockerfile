# 서버 이미지를 선택한다.
FROM amazoncorretto:11
# 서버의 포트를 열어준다.
EXPOSE 8080
# 빌드 시점에 사용될 변수를 선언
ARG JAR_FILE=build/libs/*.jar 
# 빌드 중간에 호스트의 파일 또는 폴더를 이미지에 app.jar 이름으로 가져오는 것
COPY ${JAR_FILE} ./
# 컨테이너를 구동할때 실행할 명령어를 지정한다. (java -jar /app.jar)로 실행시키는것
ENTRYPOINT ["java", "-jar", "./SendOwl-0.0.1-SNAPSHOT.jar"]
