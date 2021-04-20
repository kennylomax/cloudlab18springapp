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

    stage('test') {
      parallel {
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
ls -la'''
          }
        }

        stage('fast') {
          steps {
            unstash 'build'
            sh '''ls -la
./mvnw test -Dgroups="fast"
ls -la'''
          }
        }

      }
    }

    stage('sonarqube') {
      steps {
        withSonarQubeEnv('SonarQube') {
          sh '''./mvnw sonar:sonar
'''
        }

      }
    }

    stage('waiting') {
      steps {
        waitForQualityGate true
      }
    }

    stage('qa') {
      agent {
        docker {
          args '-p 8085:8085'
          image 'openjdk:latest'
        }

      }
      steps {
        unstash 'build'
        sh '''ls -la
java -jar target/demo-0.0.1-SNAPSHOT.jar --server.port=8085 &
'''
        input(message: 'Is this ok on port 8085?', ok: 'Yes :)')
      }
    }

  }
}