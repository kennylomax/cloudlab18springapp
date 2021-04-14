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

ls -la
'''
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
            sh '''ls -la

mvn test -Dgroups="slow"

ls -la'''
          }
        }

        stage('fast') {
          steps {
            sh '''ls -la
./mvnw test -Dgroups="fast"
ls -la'''
          }
        }

      }
    }

  }
}