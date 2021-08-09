#!/usr/bin/env bash

mvn clean package spring-boot:repackage
mvn docker:build@build