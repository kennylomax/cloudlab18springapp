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

    stage('paralleltests') {
      parallel {
        stage('slowtests') {
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

        stage('fasttests') {
          steps {
            unstash 'build'
            sh '''ls -la
./mvnw test -Dgroups="fast"
ls -la'''
          }
        }

      }
    }

  }
}