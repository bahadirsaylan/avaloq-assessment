#!/usr/bin/env bash

export ECR_REPO=$1
echo "ECR_REPO=$ECR_REPO"

echo "Pushing Api"

REPOSITORY=$2
CONTAINER_NAME=$3
VERSION=$4

echo "Login to ECR repo"
eval $(aws ecr get-login --no-include-email --region us-east-1)

docker tag $REPOSITORY/$CONTAINER_NAME:$VERSION $ECR_REPO/$REPOSITORY/$CONTAINER_NAME:$VERSION

echo "docker push $ECR_REPO/$REPOSITORY/$CONTAINER_NAME:$VERSION"
docker push $ECR_REPO/$REPOSITORY/$CONTAINER_NAME:$VERSION

if [ $? -ne 0 ]; then
  echo "Docker push failed"
  exit 1;
fi

echo "Created image $CONTAINER_NAME $ECR_REPO/$REPOSITORY/$CONTAINER_NAME:$VERSION"