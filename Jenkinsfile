node('slave001') {
    stage('Prepare') {
        echo "1.Prepare Stage"
        checkout scm
        script {
            build_tag = sh(returnStdout: true, script: 'git rev-parse --short HEAD').trim()
            if (env.BRANCH_NAME != 'master') {
                build_tag = "${env.BRANCH_NAME}-${build_tag}"
            }
        }
    }
    stage('Test') {
      echo "2.Test Stage"
    }
    stage('Build') {
        echo "3.Build Docker Image Stage"
        // sh "docker build -t docker.sz-shuwei.com/gs-spring-boot:${build_tag} ."
        sh "mvn package -Dmaven.test.skip=true"
    }
    stage('Push') {
        echo "4.Deploy jar and Push Docker Image Stage"
        sh "mvn deploy -Dmaven.test.skip=true"
        sh "docker tag docker.sz-shuwei.com/gs-spring-boot:latest docker.sz-shuwei.com/gs-spring-boot:${build_tag}" 
        withCredentials([usernamePassword(credentialsId: 'docker-register-sz-shuwei', passwordVariable: 'dockerPassword', usernameVariable: 'dockerUser')]) {
            sh "docker login -u ${dockerUser} -p ${dockerPassword} docker.sz-shuwei.com"
            sh "docker push docker.sz-shuwei.com/gs-spring-boot:${build_tag}"
        }
    }
    stage('Deploy') {
        echo "5. Deploy Stage"
        if (env.BRANCH_NAME == 'master') {
            input "确认要部署线上环境吗？"
        }
        sh "sed -i 's/<BUILD_TAG>/${build_tag}/' k8s.yaml"
        sh "sed -i 's/<BRANCH_NAME>/${env.BRANCH_NAME}/' k8s.yaml"
        sh "kubectl apply -f k8s.yaml --record"
    }
}
