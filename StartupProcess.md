# CI/CD Environment Startup Guide

This guide walks you through setting up your complete CI/CD environment using Docker Compose, configuring ZAP with a custom API key, setting up SonarQube, and configuring Jenkins for automated builds on pull requests.

## Prerequisites

- **Docker & Docker Compose**: Make sure both are installed.
- **OpenSSL**: You'll need this for generating a random API key.
- Basic knowledge of Git, Jenkins, and SonarQube is helpful.

## Step-by-Step Guide

### Step 1: Generate a Random API Key
1. Open your terminal.
2. Run the command below to generate a new API key:
   ```
   openssl rand -hex 16
   ```
3. Example output might look like this:
   ```
   a1b2c3d4e5f67890123456789abcdef0
   ```
4. **Important**: Save this key somewhere safe. You'll use it in the next steps.

### Step 2: Update the Jenkinsfile
You need to replace the hard-coded API key in your Jenkinsfile with the one you just generated.

**Update Line 70:**
- Before:
  ```
  sh 'curl "http://zap:8080/JSON/core/action/newSession/?apikey=e02e6c167c1018f6f087f95a2d64e56c" -s --proxy zap:8080'
  ```
- After:
  ```
  sh 'curl "http://zap:8080/JSON/core/action/newSession/?apikey=<YOUR_API_KEY>" -s --proxy zap:8080'
  ```

**Update Line 150:**
- Before:
  ```
  sh 'curl "http://zap:8080/OTHER/core/other/htmlreport/?apikey=e02e6c167c1018f6f087f95a2d64e56c" --proxy zap:8080 > build/reports/zap/zap_report.html'
  ```
- After:
  ```
  sh 'curl "http://zap:8080/OTHER/core/other/htmlreport/?apikey=<YOUR_API_KEY>" --proxy zap:8080 > build/reports/zap/zap_report.html'
  ```

**Remember**: Replace `<YOUR_API_KEY>` with your generated key.

### Step 3: Update the Docker Compose File
Now, update your docker-compose.yml file for the ZAP container configuration.

**Locate the ZAP container command (around Line 46):**
- Before:
  ```
  command: >
    zap.sh -daemon -host 0.0.0.0 -port 8080 -config api.key=e02e6c167c1018f6f087f95a2d64e56c -config api.addrs.addr.name=.* -config api.addrs.addr.regex=true 
  ```
- After:
  ```
  command: >
    zap.sh -daemon -host 0.0.0.0 -port 8080 -config api.key=<YOUR_API_KEY> -config api.addrs.addr.name=.* -config api.addrs.addr.regex=true 
  ```

Replace `<YOUR_API_KEY>` with your generated API key.

### Step 4: Start the Environment
1. Bring up your Docker environment:
   ```
   docker-compose up -d
   ```
   This will build and start ZAP, SonarQube, and the demo app.

2. For Jenkins, run the following commands in your terminal:
   - Change permissions for the Jenkins home directory:
     ```
     sudo chown -R 1000:1000 ./jenkins_home
     chmod -R 775 ./jenkins_home
     ```
   - Rebuild and start Jenkins:
     ```
     docker-compose up jenkins -d
     ```

3. Make sure you have the following ports set up:
   - Demo App: Port 8080
   - Jenkins: Port 8090
   - SonarQube: Port 9000

### Step 5: Configure SonarQube
#### 5.1 Access SonarQube
- Open your browser and navigate to:
  ```
  http://localhost:9000
  ```

#### 5.2 Create a New Project
- Create a new project with the exact title: ENSF400

#### 5.3 Generate a User Token
- Go to "My Account" in SonarQube.
- Create a new user token and save it.

#### 5.4 Update Gradle Configuration
- In gradle.properties, add:
  ```
  sonar.token=<YOUR_SONAR_TOKEN>
  ```
- In build.gradle (around line 450), update:
  ```
  String token = "<YOUR_SONAR_TOKEN>"  // Or retrieve it from Gradle properties / env variables
  ```

Replace `<YOUR_SONAR_TOKEN>` with your generated token.

### Step 6: Configure Jenkins
#### 6.1 Access Jenkins
- Open your browser and go to:
  ```
  http://localhost:8090
  ```

#### 6.2 Retrieve the Initial Admin Password
- Run this command in your terminal to get the password:
  ```
  docker exec -it jenkins cat /var/jenkins_home/secrets/initialAdminPassword
  ```
- Use the retrieved password to unlock Jenkins.

#### 6.3 Set Up Your Jenkins Project
- **Create a New Project**:
  - Use your GitHub repository URL.
- **Configure SCM**:
  - Set up source control management to monitor your repository.
- **Enable Webhooks**:
  - Configure the project to use GitHub webhooks.
  - Make sure automated builds are triggered on pull requests.
- **Verify the Webhook**:
  - Confirm that the webhook in your GitHub repository points to your Jenkins endpoint, e.g., `http://<your-domain-or-ip>:8090/github-webhook/`.

**Important**:
- The port 8090 should be public (not private) so GitHub can reach it.
- Make sure you configure your Docker credentials in Jenkins using the secure file provided in the submission.

#### 6.4 Permissions Fix for Docker
- Before building, run this command to adjust permissions for the Docker image build:
  ```
  sudo chmod 666 /var/run/docker.sock
  ```

## Final Notes

- **API Key**:
  - Make sure to replace the API key in your Jenkinsfile and docker-compose.yml with your generated key.
- **Ports**:
  - Verify that ports 8080, 8090, and 9000 are available and not blocked by other services.

## Conclusion

Following this guide, you've:

- Generated and applied a custom API key.
- Updated your Jenkinsfile and Docker Compose configuration.
- Started your Docker environment with ZAP, SonarQube, and the demo app.
- Configured SonarQube with a new project and updated its token.
- Set up Jenkins for automated builds via GitHub webhooks.
