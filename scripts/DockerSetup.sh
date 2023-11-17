#! /bin/bash -e

echoDockerVersion() {
    dockerVersion=$(docker --version)
    dockerMsgStr="Docker is installed with version: "
    echo $dockerMsgStr$dockerVersion
}

## installing docker
if [[ $(which docker) && $(docker --version) ]]; then
    echoDockerVersion
  else
    echo "Docker not found installing it"

    # Add Docker's official GPG key:
    sudo apt-get update -y
    sudo apt-get install -y ca-certificates curl gnupg
    sudo install -m 0755 -d /etc/apt/keyrings
    curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
    sudo chmod a+r /etc/apt/keyrings/docker.gpg

    # Add the repository to Apt sources:
    echo \
    "deb [arch="$(dpkg --print-architecture)" signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
    "$(. /etc/os-release && echo "$VERSION_CODENAME")" stable" | \
    sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
    sudo apt-get update -y

    # Installing docker engine
    sudo apt-get install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
    echoDockerVersion
fi



if command -v docker-compose &> /dev/null
then
    echo "docker-compose is installed."
else
    echo "docker-compose is not installed. Installing it..."
    sudo apt-get install -y docker-compose
    echo "creating portainer container ..."
    docker run -d \
        -p 9000:9000 \
        --name=portainer_container \
        --restart=always \
        -v /var/run/docker.sock:/var/run/docker.sock \
        -v portainer_data:/data \
        portainer/portainer

fi

docker-compose stop 
# stopping kafka containers
docker-compose -f docker-compose-kafka-all.yml stop

echo "all porduction container stopped ..."
docker-compose up -d
# starting kafka containers
#docker-compose -f docker-compose-kafka-all.yml up -d


