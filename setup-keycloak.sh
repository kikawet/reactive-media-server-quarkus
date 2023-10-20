#!/usr/bin/bash

set -xe

# TODO: use docker instead of locally installed program

KCPORT=${KCPORT:-8543}

sudo kc.sh start-dev --http-port $KCPORT --https-key-store-file=config/keycloak-keystore.jks
