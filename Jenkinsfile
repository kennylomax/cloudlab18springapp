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
        sh '''ls
mvn install -DskipTests
ls'''
        stash(name: 'build', includes: '**/target/**')
      }
    }

    stage('tests') {
      parallel {
        stage('fast') {
          agent {
            docker {
              image 'maven:3.6-jdk-11-slim'
            }

          }
          steps {
            unstash 'build'
            sh '''ls -R target

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
            sh 'mvn test -Dgroups="slow"'
          }
        }

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
        sh '''ls target

java -jar target/demo-0.0.1-SNAPSHOT.jar --server.port=8085 &

'''
        input(message: 'Ok?', ok: 'Go go go!')
      }
    }

    stage('dockerbuildpush') {
      environment {
        DOCKER_HUB_LOGIN = credentials('docker-hub')
      }
      steps {
        unstash 'build'
        sh '''ls -la

docker login --username=$DOCKER_HUB_LOGIN_USR --password=$DOCKER_HUB_LOGIN_PSW
docker build -t kenlomax/test1:v1 .
docker push kenlomax/test1:v1
     

ls -la
'''
      }
    }

    stage('sonar') {
      steps {
        withSonarQubeEnv('SonarQube') {
          sh '''./mvnw clean package sonar:sonar
#./gradlew sonarqube'''
        }

      }
    }

    stage('wait') {
      steps {
        waitForQualityGate true
      }
    }

  }
}