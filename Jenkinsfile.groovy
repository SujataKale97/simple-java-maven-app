node('master') {
    git credentialsId: 'ea4c3770-b2ed-4639-9ffc-cc3e586e454c', url: 'https://github.com/SujataKale97/simple-java-maven-app.git'
    bat label: '', script: 'mvn verify'
}
