pipeline {
  agent any
  stages {
    stage('build') {
      agent {
        docker {
          image 'maven:3.6-jdk-11-slim'
        }

      }
      steps {
        sh '''ls -laR
mvn install -DskipTests
ls -laR
'''
      }
    }

    stage('test') {
      steps {
        sh 'ls -laR'
      }
    }

  }
}