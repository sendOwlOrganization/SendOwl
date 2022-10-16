bash gradlew clean
bash gradlew build -x test

docker build --tag sendowlproj123/main:application .
docker push sendowlproj123/main:application
