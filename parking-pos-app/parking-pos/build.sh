#!/bin/bash

# Install dependencies
echo "Installing dependencies..."
npm install --omit=optional

# Build the application
echo "Building application..."
npm run build

echo "Build completed successfully!"
