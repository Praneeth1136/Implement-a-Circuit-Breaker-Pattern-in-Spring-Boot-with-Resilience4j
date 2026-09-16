#!/bin/bash

BASE_URL="http://localhost:8080"
API_URL="${BASE_URL}/api/users/1"
STATE_URL="${BASE_URL}/api/circuit-breaker/state"
EXTERNAL_RESET_URL="${BASE_URL}/external/api/reset"

echo "==================================================="
echo "Testing Resilience4j Circuit Breaker"
echo "==================================================="

# Reset the external service counter
curl -s -X GET ${EXTERNAL_RESET_URL} > /dev/null
echo "External service counter reset."

echo -e "\n1. Initial State:"
curl -s -X GET ${STATE_URL}
echo ""

echo -e "\n2. Making 10 requests to trip the Circuit Breaker:"
for i in {1..10}
do
   echo -n "Request $i: "
   curl -s -X GET ${API_URL} | grep -o 'default-id\|Real User' > /dev/null
   if [ $? -eq 0 ]; then
       # we can just print the output
       curl -s -X GET ${API_URL}
   fi
   echo ""
done

echo -e "\n3. State after requests:"
curl -s -X GET ${STATE_URL}
echo ""

echo -e "\n4. Waiting for 11 seconds (wait-duration-in-open-state is 10s)..."
sleep 11

echo -e "\n5. State after waiting:"
curl -s -X GET ${STATE_URL}
echo ""

echo -e "\n6. Making a request to transition to HALF_OPEN -> CLOSED:"
curl -s -X GET ${API_URL}
echo ""

echo -e "\n7. Final State:"
curl -s -X GET ${STATE_URL}
echo ""

echo -e "\n==================================================="
echo "Test Complete"
echo "==================================================="
