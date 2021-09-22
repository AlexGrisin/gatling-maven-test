pipeline {
    agent any

    options {
        skipDefaultCheckout true
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Initialize') {
            steps {
               sh '''
               mvn clean install
               '''
            }
        }
        stage('Run Simulation') {
            steps {
               sh '''
               mvn gatling:test -Dgatling.simulationClass=simulations.${simulation_name} -Denv=${environment}
               '''
            }
            post {
                always {
                    gatlingArchive()
                }
            }
        }
    }
}