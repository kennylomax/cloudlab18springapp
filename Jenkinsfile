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
        stash(includes: '**/target/**', name: 'build')
      }
    }

    stage('test') {
      parallel {
        stage('fast') {
          agent {
            docker {
              image 'maven:3.6-jdk-11-slim'
            }

          }
          steps {
            unstash 'build'
            sh '''ls -la
mvn test -Dgroups="fast"
'''
          }
        }

        stage('slow') {
          agent {
            docker {
              image 'maven:3.6-jdk-11-slim'
            }

          }
          steps {
            unstash 'build'
            sh '''ls -la
mvn test -Dgroups="slow"
'''
          }
        }

      }
    }

    stage('sonarq') {
      steps {
        withSonarQubeEnv('SonarQube') {
          sh './mvnw clean package sonar:sonar'
        }

      }
    }

    stage('waitForSonarQ') {
      steps {
        waitForQualityGate true
      }
    }

    stage('manualqa') {
      steps {
        unstash 'build'
        sh '''ls -la
java -jar target/demo-0.0.1-SNAPSHOT.jar --server.port=8085 &'''
        input(message: 'La de da on 8085?', ok: 'Go go go!')
      }
    }

  }
}