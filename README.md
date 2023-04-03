Test app showing simple app for bookings with [mu-server].

You can run locally by running `App#main` or try building an uber jar.

### Building the uber jar

    mvn package
    java -jar target/mu-server-sample-1.0.jar


To create a new book:

    curl --location --request POST 'http://localhost:18080/api/booking' \
    --header 'Authorization: Basic dXNlcjp1c2Vy' \
    --header 'Content-Type: application/json' \
    --data-raw '{
    "customerName" : "Mary Doe",
    "tableSize" : 8,
    "reservedAt" : "2023-04-03"
    }'

To get bookings list:

    curl --location --request GET 'http://localhost:18080/api/booking' \
    --header 'Authorization: Basic YWRtaW46YWRtaW4='

Every request should be authenticated. Basic authorization is used.

    user:user
    admin:admin