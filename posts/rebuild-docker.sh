./gradlew clean bootJar
docker build -t epamhleblitvinau/posts:1.0.0 ./
docker login
docker push epamhleblitvinau/posts:1.0.0
