#! /bin/bash -e

for dir in todo todo-discovery todo-gateway; do
	(cd $dir ; ./gradlew clean build $*)
done

docker build -t todo:master --file=todo/Dockerfile --force-rm todo
docker build -t todo-discovery:master --file=todo-discovery/Dockerfile --force-rm todo-discovery
docker build -t todo-gateway:master --file=todo-gateway/Dockerfile --force-rm todo-gateway