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
        stash(name: 'build', includes: '**/target/**')
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
mvn test -Dgroups="fast"'''
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
mvn test -Dgroups="slow"'''
          }
        }

      }
    }

    stage('sonarq') {
      steps {
        withSonarQubeEnv('SonarQube') {
          sh '''ls -la
./mvnw clean package sonar:sonar
ls -la
'''
        }

      }
    }

    stage('waitsq') {
      steps {
        waitForQualityGate true
      }
    }

    stage('manualqa') {
      agent {
        docker {
          image 'openjdk:latest'
          args '-p 8085:8085'
        }

      }
      steps {
        unstash 'build'
        sh '''ls -la
java -jar target/demo-0.0.1-SNAPSHOT.jar --server.port=8085 &
'''
        input(message: 'Ok?', ok: 'Oui oui!')
      }
    }

    stage('dockerbuildpush') {
      steps {
        sh '''ls -la
docker login --username=$DOCKER_HUB_LOGIN_USR --password=$DOCKER_HUB_LOGIN_PSW
docker build -t kenlomax/test1:v1 .
docker push kenlomax/test1:v1
'''
      }
    }

  }
}