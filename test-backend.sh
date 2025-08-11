#!/bin/bash

# Vikings Rise Simulator - Backend Test Script

echo "🧪 Testing backend setup..."

cd backend

# Test compilation
echo "📦 Testing Maven compilation..."
mvn compile -q
if [ $? -ne 0 ]; then
    echo "❌ Backend compilation failed"
    exit 1
fi

echo "✅ Backend compilation successful"

# Test if main class exists
if [ -f "src/main/java/com/vikingsrise/simulator/SimulatorBackendApplication.java" ]; then
    echo "✅ Main application class found"
else
    echo "❌ Main application class not found"
    exit 1
fi

# Test if test classes exist
if [ -d "src/main/java/test" ]; then
    echo "✅ Original simulation classes found"
else
    echo "❌ Original simulation classes not found"
    exit 1
fi

# Test if resources exist
if [ -f "src/main/resources/test/SkillDatabase.json" ]; then
    echo "✅ Skill database found"
else
    echo "❌ Skill database not found"
    exit 1
fi

echo "🎉 Backend setup validation completed successfully!"

cd ..
