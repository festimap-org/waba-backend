#!/usr/bin/env bash
curl -fs http://localhost:9998/ | grep -q '"status":"UP"'