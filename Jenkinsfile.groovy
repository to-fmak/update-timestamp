pipeline {
    agent any

    stages {
        stage('Update Timestamp') {
            steps {
                script {
                    env.TZ = 'Asia/Tokyo'
                    sh 'echo `date` > timestamp.txt'
                }
            }
        }

        stage('Commit and Push File') {
            steps {
                script {
                    withCredentials([
                        gitUsernamePassword(credentialsId: "${params.GIT_CREDENTIAL}", gitToolName: 'Default')
                    ]) {
                        sh 'git add timestamp.txt'
                        sh 'git commit -m "Update Timestamp"'
                        sh "git push origin HEAD:${params.BRANCH}"
                    }
                }
            }
        }
    }
}
