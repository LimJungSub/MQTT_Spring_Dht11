#!/bin/zsh

echo startCommandPub
mosquitto_pub -t webIot -p 1883 -m "initial Message"