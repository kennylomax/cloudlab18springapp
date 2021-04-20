pipeline {
  agent any
  stages {
    stage('build') {
      steps {
        sh '''ls -la

mvn install -DskipTests

ls -la'''
      }
    }

  }
}