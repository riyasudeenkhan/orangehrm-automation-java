pipeline {
    agent any

    // tools {
    //     maven 'Maven-3.6.0'   // Adjust as per your Jenkins Maven installation name
    //     jdk 'JDK-17.0'          // Adjust as per your Jenkins JDK installation name
    // }
    parameters {
        choice(
            name: 'TESTNG_FILE',
            choices: ['testng.xml', 'testng-api.xml', 'testng-parallel.xml'],
            description: 'Select TestNG suite file'
        )
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/riyasudeenkhan/orangehrm-automation-java.git'
            }
        }

        stage('Echo Param') {
            steps {
                echo "Suite selected: ${params.TESTNG_FILE}"
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
