#!/bin/zsh

echo startCommandPub
mosquitto_pub -t SpringIot -p 1883 -m "initial Message"