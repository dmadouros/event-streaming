Start Kafka & Zookeeper:
```bash
docker-compose up -d
```

Start Consumer (enriched-events):
```bash
docker exec --interactive --tty broker \
    kafka-console-consumer --bootstrap-server broker:9092 \
                           --topic enriched-events \
                           --from-beginning
```

Start Consumer (bad-events):
```bash
docker exec --interactive --tty broker \
    kafka-console-consumer --bootstrap-server broker:9092 \
                           --topic bad-events \
                           --from-beginning
```

Start Producer:
```bash
docker exec --interactive --tty broker \
    kafka-console-producer --bootstrap-server broker:9092 \
                           --topic raw-events-ch03
```

Build Stream App:
```bash
./gradlew ktlintFormat && ./gradlew build -x test
```

Run Stream App:
```bash
./gradlew run --args="localhost:9092 ulp-ch03-3.4 raw-events-ch03 enriched-events bad-events $(pwd)/GeoLite2-City_20220617/GeoLite2-City.mmdb"
```

Good Events:
```json
{ "event": "SHOPPER_VIEWED_PRODUCT", "shopper": { "id": "123", "name": "Jane", "ipAddress": "70.46.123.145" }, "product": { "sku": "aapl-001", "name": "iPad" }, "timestamp": "2018-10-15T12:01:35Z" }
```
```json
{ "event": "SHOPPER_VIEWED_PRODUCT", "shopper": { "id": "456", "name": "Mo", "ipAddress": "89.92.213.32" }, "product": { "sku": "sony-072", "name": "Widescreen TV" }, "timestamp": "2018-10-15T12:03:45Z" }
```
```json
{ "event": "SHOPPER_VIEWED_PRODUCT", "shopper": { "id": "789", "name": "Justin", "ipAddress": "97.107.137.164" }, "product": {"sku": "ms-003", "name": "XBox One" }, "timestamp": "2018-10-15T12:05:05Z" }
```

Bad Events:
```json
{ "event": "SHOPPER_VIEWED_PRODUCT", "shopper": { "id": "456", "name": "Mo", "ipAddress": "not an ip address" }, "product": { "sku": "sony-072", "name": "Widescreen TV" }, "timestamp": "2018-10-15T12:03:45Z" }
```
```json
{ "event": "SHOPPER_VIEWED_PRODUCT", "shopper": {}, "timestamp": "2018-10-15T12:05:05Z" }
```