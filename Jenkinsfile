pipeline {
    agent any

    // tools {
    //     maven 'Maven-3.6.0'   // Adjust as per your Jenkins Maven installation name
    //     jdk 'JDK-17.0'          // Adjust as per your Jenkins JDK installation name
    // }
    parameters {
        string(name: 'TESTNG_FILE', defaultValue: 'testng.xml', description: 'Name of the TestNG suite XML file')
    }

    stages {
        stage('Debug') {
            steps {
                echo 'Pipeline started'
            }
        }

        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/riyasudeenkhan/orangehrm-automation-java.git'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test -DsuiteXmlFile=${params.TESTNG_FILE}'
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
