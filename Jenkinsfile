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
                    echo "building the spring application"
                    sh 'mvn package'
                }
            }
        }
        stage("build image") {
            steps {
                script {
                    echo "building the docker image and push than image to docker hub..."
                    //gv.buildImage()
                    withCredentials([usernamePassword(credentialsId: 'docker-hub-pm310', passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]){
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
                    echo "deploying the spring application on ec2"
                    //gv.deployApp()
                    def dockerCreate="docker run -p 8080:8080 --name ec2-spring ${IMAGE_NAME}"
                    sshagent(['ec2-lakhu-key']) {
                        sh "ssh -o StrictHostKeyChecking=no ec2-user@34.224.156.103 ${dockerCreate}"

                    }
                }
            }
        }
    }
}
