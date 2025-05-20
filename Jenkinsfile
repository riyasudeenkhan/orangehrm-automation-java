pipeline {
    agent any

    tools {
        maven 'Maven-3.6.0'   // Adjust as per your Jenkins Maven installation name
        jdk 'JDK-17.0'          // Adjust as per your Jenkins JDK installation name
    }

    stages {
        stage('Debug') {
            steps {
                echo 'Pipeline started'
            }
        }

        stage('Checkout') {
            steps {
                git 'https://github.com/riyasudeenkhan/orangehrm-automation-java.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Publish Reports') {
            steps {
                junit '**/target/surefire-reports/*.xml'
            }
        }
    }

    post {
        always {
            echo "Pipeline completed."
        }
        success {
            echo "Pipeline succeeded."
        }
        failure {
            echo "Pipeline failed."
        }
    }
}
