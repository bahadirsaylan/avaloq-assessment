# Dice Roll Simulator
This project contains rest API endpoints for performing random dice rolling simulations.

## API definitions
To visit full API definitions go to: `http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config`

## Postman Collection

Postman collection located in `/postman` folder

## Endpoints
* `POST /v1/simulation` - runs the simulation with following request body:
```
  {
   "dices": "{numberOfDice}",
   "sides": "{numberOfSides}",
   "rolls": "{numberOfRolls}"
  }
```
* `GET /v1/simulation` - returns all simulations
* `GET /v1/simulation/result` - returns all simulation results
* `GET /v1/simulation/stats?dices={numberOfDice}&sides={numberOfSides}` - returns stats for given number of dices and sides
* `GET /v1/simulation/distributions?dices={numberOfDice}&sides={numberOfSides}` - returns overall distributions for given dices and sides numbers
* `GET /v1/simulation/distribution?dices={numberOfDice}&sides={numberOfSides}&dicesSum={dicesSum}` - return a random distribution for `numberOfDice` dices, with `numberOfSides` sides and for `dicesSum`.

All parameters for above methods are required. If any parameter is missing returns response with status.

## Building
Requirements:

- Java > 1.8
- Docker > 20.10.5

In order to build the executable jar:

```$ mvn clean package spring-boot:repackage``` then

To build docker image:

```$ mvn docker:build@build```

OR to run all tests and build all:

```$ ./build.sh```

The resulting ```avaloq-api.jar ``` will be located in ```target/``` and

```avaloq/api  0.0.1-SNAPSHOT``` docker image will be created.

##Testing

In order to run only tests:

```$ mvn clean test```

##Run Docker

In order to run docker container:

```$ docker run avaloq/api:0.0.1-SNAPSHOT```

##Run SpringBoot

```$ mvn spring-boot:run```

##Push Docker Image to ECR

Install AWS CLI and then run following command to push docker image to ECR registry.

```$ ./push_ecr <ecr-registry> <repository> <container-name> <version>```