#!/usr/bin/bash

set -xe

DBHOST=${DBHOST:-localhost}
DBNAME=${DBNAME:-quarkus}
DBPWD=${DBPWD:-rmsPassword}
DBUSR=${DBUSR:-rmsUser}
DBNAME=${DBNAME:-rmsDB}
DBPORT=${DBPORT:-32723}

PGPASSWORD=$DBPWD psql $DBNAME -h $DBHOST -p $DBPORT -U $DBUSR \
        -f "aggregate/ema.sql" \
        -f "tables/suggestions-base.sql" \
        -f "views/non-view.sql" \
        -f "views/suggestion.sql" \
        -f "views/user-video-meta.sql" \
        -f "triggers/after_insert_view_update_suggestion.sql" \