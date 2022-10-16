sudo kill -9 `ps -ef| grep build/libs/SendOwl-0.0.1-SNAPSHOT.jar | awk '{print $2}'`
sudo nohup java -jar build/libs/SendOwl-0.0.1-SNAPSHOT.jar 1>sendowllog.out 2>sendowllog.err &
exit 0
