#!/bin/bash

# Vikings Rise Simulator Web Edition - Start Script

echo "🚀 Starting Vikings Rise Simulator Web Edition..."

# Function to kill background processes on exit
cleanup() {
    echo "🛑 Shutting down..."
    kill $BACKEND_PID $FRONTEND_PID 2>/dev/null
    exit 0
}

# Set up signal handler
trap cleanup SIGINT SIGTERM

# Start backend
echo "🔧 Starting backend server..."
cd backend
mvn spring-boot:run &
BACKEND_PID=$!
cd ..

# Wait a moment for backend to start
sleep 5

# Start frontend
echo "🎨 Starting frontend server..."
cd frontend
npm start &
FRONTEND_PID=$!
cd ..

echo "✅ Both servers started!"
echo "🌐 Frontend: http://localhost:3000"
echo "🔧 Backend:  http://localhost:8080"
echo ""
echo "Press Ctrl+C to stop both servers"

# Wait for either process to end
wait
