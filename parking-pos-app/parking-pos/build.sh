#!/bin/bash

# Clean install dependencies
echo "Installing dependencies..."
npm ci --only=production --ignore-scripts

# Build the application
echo "Building application..."
npm run build

echo "Build completed successfully!"
