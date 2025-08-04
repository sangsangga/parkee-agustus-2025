#!/bin/bash

LOGFILE="./log_update_$(date +%Y%m%d_%H%M%S).log"

echo "Start Updating Package $(date)" | tee -a "$LOGFILE"

sudo apt update | tee -a "$LOGFILE"
sudo apt upgrade -y | tee -a "$LOGFILE"
sudo apt autoremove -y | tee -a "$LOGFILE"

echo "Finish Updating Package $(date)" | tee -a "$LOGFILE"