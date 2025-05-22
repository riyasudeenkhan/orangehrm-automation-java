pipeline {
    agent any
    
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
                withEnv(["TESTNG_FILE=${params.TESTNG_FILE}"]) {
                    bat "mvn test -DsuiteXmlFile=%TESTNG_FILE%"
                }
            }
        }        
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml'
            allure includeProperties: false, jdk: '', results: [[path: 'target/allure-results']]
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
