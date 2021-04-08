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

  }
}