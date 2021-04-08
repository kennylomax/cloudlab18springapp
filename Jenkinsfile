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
./mvnw test -Dgroups="slow"
ls -la'''
          }
        }

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
ls -la'''
          }
        }

      }
    }

    stage('SonarQube') {
      steps {
        withSonarQubeEnv('SonarQube') {
          sh './mvnw clean package sonar:sonar'
        }

      }
    }

    stage('SonarQubeWait') {
      steps {
        waitForQualityGate true
      }
    }

    stage('QAManualCheck') {
      agent {
        docker {
          image 'openjdk:latest'
          args '-p 8085:8085'
        }

      }
      steps {
        unstash 'build'
        sh '''ls -la
java -jar target/demo-0.0.1-SNAPSHOT.jar --server.port=8085 &'''
        input(message: 'Hey guys is this ok on port 8085', ok: 'Oui oui!')
      }
    }

    stage('DockerBuildPush') {
      steps {
        sh '''ls -la
docker login --username=$DOCKER_HUB_LOGIN_USR --password=$DOCKER_HUB_LOGIN_PSW
docker build -t kenlomax/test1:v1 .
docker push kenlomax/test1:v1'''
      }
    }

  }
}