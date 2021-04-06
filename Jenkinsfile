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
        sh '''ls -la
mvn install -D skipTests
ls -la'''
      }
    }

    stage('test') {
      parallel {
        stage('fast') {
          steps {
            sh '''ls -la
mvn test -Dgroups="fast"'''
          }
        }

        stage('slow') {
          steps {
            sh '''ls -la
mvn test -Dgroups="slow"'''
          }
        }

      }
    }

  }
}