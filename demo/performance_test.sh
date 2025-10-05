#!/bin/bash

# Performance Testing Script for Vikings Rise Simulator
# Tests the optimized version and compares with baseline

echo "=== Vikings Rise Simulator Performance Test ==="
echo "Starting performance monitoring..."

TIMESTAMP=$(date +%Y%m%d_%H%M%S)
RESULTS_DIR="performance_results_$TIMESTAMP"
mkdir -p "$RESULTS_DIR"

echo "Results will be saved to: $RESULTS_DIR"

# Test 1: Basic performance profile (2 minutes)
echo "Test 1: Running 2-minute performance profile..."
java -XX:StartFlightRecording=duration=120s,filename=$RESULTS_DIR/basic_profile.jfr,settings=profile \
     -jar "Vikings Rise Simulator.jar" &
PID1=$!

sleep 5
echo "Application started (PID: $PID1). Please run some simulations..."
echo "The profiling will automatically stop after 2 minutes."

wait $PID1

# Test 2: Detailed analysis (3 minutes) 
echo -e "\nTest 2: Running detailed analysis..."
java -XX:StartFlightRecording=duration=180s,filename=$RESULTS_DIR/detailed_profile.jfr,settings=profile \
     -XX:FlightRecorderOptions=stackdepth=128 \
     -jar "Vikings Rise Simulator.jar" &
PID2=$!

sleep 5
echo "Detailed profiling started (PID: $PID2)..."
echo "Please run intensive simulations (trades, fights, group sims)..."

wait $PID2

# Analysis
echo -e "\n=== Analysis ==="
echo "JFR files created:"
ls -la "$RESULTS_DIR"/*.jfr

echo -e "\nAnalyzing shouldTrigger() performance..."
if command -v jfr &> /dev/null; then
    echo "Basic profile shouldTrigger samples:"
    jfr print --events jdk.ExecutionSample "$RESULTS_DIR/basic_profile.jfr" | grep -c "test.Skill.shouldTrigger" || echo "0"
    
    echo "Detailed profile shouldTrigger samples:"
    jfr print --events jdk.ExecutionSample "$RESULTS_DIR/detailed_profile.jfr" | grep -c "test.Skill.shouldTrigger" || echo "0"
    
    echo -e "\nGenerating summary reports..."
    jfr summary "$RESULTS_DIR/basic_profile.jfr" > "$RESULTS_DIR/basic_summary.txt"
    jfr summary "$RESULTS_DIR/detailed_profile.jfr" > "$RESULTS_DIR/detailed_summary.txt"
    
    echo "Summary reports created in $RESULTS_DIR/"
else
    echo "jfr command not found. Please analyze .jfr files manually with JDK Mission Control or jfr command."
fi

echo -e "\n=== Performance Test Complete ==="
echo "Results saved in: $RESULTS_DIR/"
echo ""
echo "To analyze results:"
echo "1. Open .jfr files with JDK Mission Control (jmc)"
echo "2. Or use: jfr print --events jdk.ExecutionSample filename.jfr"
echo "3. Look for reduced samples in test.Skill.shouldTrigger()"