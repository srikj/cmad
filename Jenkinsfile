pipeline {
  agent any
  stages {
    stage('Docker Tests') {
      steps {
        parallel(
          "Resource": {
            sh 'docker run -it --rm --name my-maven-project -v "$PWD":/usr/src/mymaven -w /usr/src/mymaven maven:3.2-jdk-7 mvn test -DsuiXmlFile=src/test/java/com/cisco/sdp/test/components/resourcemgmt/workflowexecutor/ResourceMgmtWorkflowExecutorServiceSuite.xml'
            
          },
          "Group": {
            sh 'docker run -it --rm --name my-maven-project -v "$PWD":/usr/src/mymaven -w /usr/src/mymaven maven:3.2-jdk-7 mvn test -DsuiXmlFile=src/test/java/com/cisco/sdp/test/components/group/GroupManagementServiceSuite.xml'
            
          }
        )
      }
    }
  }
}