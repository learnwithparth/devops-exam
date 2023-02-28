pipeline {
    agent any
    environment{
        IMAGE_NAME= 'pm310/spring_exam-app:ra-1.0'
    }

    stages {
        stage("init") {
            steps {
                script {
                    gv = load "script.groovy"
                }
            }
        }
        stage("build jar") {
            steps {
                script {
                    echo "building the react application"
                    buildJar()
                }
            }
        }
        stage("build image") {
            steps {
                script {
                    echo "building the docker image and restart container..."
                    //gv.buildImage()
                    withCredentials([usernamePassword(credentialsId: 'docker', passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]){
                        sh 'docker build -t ${IMAGE_NAME} .'
                        sh 'docker login -u $USERNAME -p $PASSWORD'
                        sh 'docker push ${IMAGE_NAME}'
                    }
                }
            }
        }
        stage("deploy") {
            steps {
                script {
                    echo "deploying the application on ec2"
                    //gv.deployApp()
                    def dockerComposeCmd="docker-compose -f docker-compose.yaml up --detach"
                    sshagent(['ec2-ubuntu-key']) {
                        sh "scp docker-compose.yaml ec2-user@54.95.222.132:/home/ec2-user"
                        sh "ssh -o StrictHostKeyChecking=no ec2-user@54.95.222.132 ${dockerComposeCmd}"

                    }
                }
            }
        }
    }
}