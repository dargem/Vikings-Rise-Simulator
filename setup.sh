#!/bin/bash

# Vikings Rise Simulator Web Edition - Setup Script

echo "🏗️  Setting up Vikings Rise Simulator Web Edition..."

# Check if Node.js is installed
if ! command -v node &> /dev/null; then
    echo "❌ Node.js is not installed. Please install Node.js 16+ and try again."
    exit 1
fi

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "❌ Java is not installed. Please install Java 17+ and try again."
    exit 1
fi

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven is not installed. Please install Maven 3.6+ and try again."
    exit 1
fi

echo "✅ Prerequisites check passed"

# Setup Frontend
echo "📦 Installing frontend dependencies..."
cd frontend
npm install
if [ $? -ne 0 ]; then
    echo "❌ Failed to install frontend dependencies"
    exit 1
fi
cd ..

# Setup Backend
echo "📦 Installing backend dependencies..."
cd backend
mvn clean install -q
if [ $? -ne 0 ]; then
    echo "❌ Failed to install backend dependencies"
    exit 1
fi
cd ..

echo "✅ Setup completed successfully!"
echo ""
echo "🚀 To start the application:"
echo "   1. Start backend:  cd backend && mvn spring-boot:run"
echo "   2. Start frontend: cd frontend && npm start"
echo ""
echo "🌐 The application will be available at:"
echo "   Frontend: http://localhost:3000"
echo "   Backend:  http://localhost:8080"
