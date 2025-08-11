#!/bin/bash

echo "Testing Vikings Rise Simulator API endpoints..."
echo

echo "1. Testing Health endpoint:"
curl -s http://localhost:8080/health | python3 -c "import sys, json; print(json.dumps(json.load(sys.stdin), indent=2))"
echo

echo "2. Testing API Info endpoint:"
curl -s http://localhost:8080/api | python3 -c "import sys, json; print(json.dumps(json.load(sys.stdin), indent=2))"
echo

echo "3. Testing Skills endpoint (first 5):"
curl -s http://localhost:8080/api/skills | python3 -c "import sys, json; skills = json.load(sys.stdin); print(json.dumps(skills[:5], indent=2))"
echo

echo "4. Testing Commanders endpoint:"
curl -s http://localhost:8080/api/commanders | python3 -c "import sys, json; print(json.dumps(json.load(sys.stdin), indent=2))"
echo

echo "5. Testing Simulation endpoint with correct data structure:"
curl -s -X POST -H "Content-Type: application/json" -d '{
  "config": {
    "type": "trades",
    "rounds": 10,
    "fights": 1,
    "fightLength": 300,
    "fightsPerLength": 1
  },
  "marches": [
    {
      "id": "march1",
      "primaryCommander": "Vali",
      "secondaryCommander": "N/A",
      "attack": 100.0,
      "defense": 100.0,
      "health": 100.0,
      "troopSize": 10000,
      "isFriendly": true,
      "isRally": false,
      "skills": ["Divine Blessing", "First Strike"]
    },
    {
      "id": "march2",
      "primaryCommander": "Gunnar", 
      "secondaryCommander": "N/A",
      "attack": 100.0,
      "defense": 100.0,
      "health": 100.0,
      "troopSize": 10000,
      "isFriendly": false,
      "isRally": false,
      "skills": ["Enrage", "Fiery Rage"]
    }
  ]
}' http://localhost:8080/api/simulate

echo
echo "API testing completed!"
