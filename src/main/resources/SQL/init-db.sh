#!/usr/bin/bash

set -xe

DBHOST=${DBHOST:-localhost}
DBPWD=${DBPWD:-rmsPassword}
DBUSR=${DBUSR:-rmsUser}
DBNAME=${DBNAME:-rmsDB}
DBPORT=${DBPORT:-32784}

PGPASSWORD=$DBPWD psql -l -h $DBHOST -p $DBPORT -U $DBUSR