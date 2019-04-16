node('master') {
    git 'https://github.com/SujataKale97/simple-java-maven-app.git'
    bat 'mvn verify'
}
