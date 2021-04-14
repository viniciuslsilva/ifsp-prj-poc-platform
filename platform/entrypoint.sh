#!/bin/sh

echo "
spring.jackson.serialization.FAIL_ON_EMPTY_BEANS=false

fake.external.service.host=${FAKE_EXTERNAL_SERVICE_HOST}
fake.external.service.port=${FAKE_EXTERNAL_SERVICE_PORT}
" > /platform.properties;

java -jar "platform.jar" \
    --spring.config.location=/platform.properties