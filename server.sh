#!/bin/bash

# Run up an SSH-enabled Apache in the current directory.

docker run --rm --name httpd-ssi -p 5000:80 \
       -v "$PWD":/usr/local/apache2/htdocs/ \
       cassiel/httpd-ssi
