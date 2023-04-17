./gradlew clean bootJar
docker build -t epamhleblitvinau/users:1.0.0 ./
docker login
docker push epamhleblitvinau/users:1.0.0
