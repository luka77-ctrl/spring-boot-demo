# spring-boot-demo-tio

This module demonstrates how to integrate the [t-io](https://github.com/tywo45/t-io) networking framework with Spring Boot. A lightweight WebSocket server is started when the application launches.

## Run

```bash
mvn -pl demo-tio spring-boot:run
```

The server listens on port `9876` by default. Connect with any WebSocket client and any text message you send will be echoed back.

