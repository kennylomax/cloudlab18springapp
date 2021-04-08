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
mvn install -DskipTests
ls -la'''
        stash(name: 'build', includes: '**/target/**')
      }
    }

    stage('tests') {
      parallel {
        stage('slow') {
          steps {
            unstash 'build'
            sh '''ls -la
mvn test -Dgroups="slow"
ls -la'''
          }
        }

        stage('fast') {
          steps {
            unstash 'build'
            sh '''ls -la
mvn test -Dgroups="fast"
ls -la'''
          }
        }

      }
    }

  }
}