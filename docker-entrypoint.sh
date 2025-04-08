#!/bin/bash
set -e

echo "Fixing permissions on /var/jenkins_home..."
chown -R 1000:1000 /var/jenkins_home
chmod -R 775 /var/jenkins_home




echo "WARNING: Applying insecure permissions (666) to /var/run/docker.sock!"
chmod 666 /var/run/docker.sock || echo "WARNING: Failed to chmod docker.sock. This command might require root privileges or for the container to be run as --privileged (which is also insecure)."

exec /usr/local/bin/jenkins.sh "$@"