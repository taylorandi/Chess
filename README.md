# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared tests`     | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

### Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```

https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDAEooDmSAzmFMARDQVqhFHXyFiwUgBF+wAIIgQKLl0wATeQCNgXFDA3bMmdlAgBXbDADEaYFQCerDt178kg2wHcAFkjAxRFRSAFoAPnJKGigALhgAbQAFAHkyABUAXRgAegt9KAAdNABvfMp7AFsUABoYXDVvaA06lErgJAQAX0xhGJgIl04ePgEhaNF4qFceSgAKcqgq2vq9LiaoFpg2joQASkw2YfcxvtEByLkwRWVVLnj2FDAAVQKFguWDq5uVNQvDbTxMgAUQAMsC4OkYItljAAGbmSrQgqYb5KX5cAaDI5uUaecYiFTxNAWBAIQ4zE74s4qf5o25qeIgab8FCveYw4DVOoNdbNL7ydF3f5GeIASQAciCWFDOdzVo1mq12p0YJL0ilkbQcSMPIIaQZBvSMUyWYEFBYwL53hUuSgBdchX9BqK1VLgTKtUs7XVgJbfOkIABrdBujUwP1W1GChmY0LYyl4-UTIkR-2BkNoCnHJMEqjneORPqUeKRgPB9C9aKULGRYLoMDxABMAAYW8USmWM+geugNCYzJZrDZoNJHjBQRBOGgfP5Aph62Ei9W4olUhlsjl9Gp8R25SteRsND1i1AIjqqcnCSh4pPpzbvfLD-zs7i9XnRBFjXcHk92VAH0+aNHVjCJXRBcFIS9WEEQgJFFmAn47kLC9cwNeIENQ98DS-GMTRgZkUFZC0rUAu0HSQtQwMBN1pVlD47TTK1uxndVNTLRCnS4FDE2wlMbyY8tM1fXVTn4wtT1LdMKyzU9C0XRsYFbFsYA7LsZN7NB+1McwrFsMwUFDO9LGYGw-ACIJkAbf5JMSGQwWBdJgU3bcuF3expOEuTwiwsTryk5iZJEy8PxUXCQPwmQUAQJ4UBI611MzCiuOo+J7PBJzBJYzjQPPXi-PzIlgrQ8SIkkqtqEoeSrIwWJlMwPsB104dpg0Cc3BgABxO1MTMudLJCZhBlshJOsczd2DtYpEsrbzfOpfiAqEyt5qvQqUHCyj7iyoLvyo8JwIcqDJuqTFYKRMsYEgYS9u4vKcz4-yYBO1RQTcYrHvWiSV1iF6uDengKpiarBviBJlMyYoYBKGAACI-thzTtMHPSbGwCwoGwGL4DNAxuuqWcLIXGqhuXSrV2SNIslyF7ps89AOxeiU7RPFd7rfArJkI1l8ZQOYmfIj7ObC8JbtNIjAglFBvF5-m7WZ6pktAg6aMlOjnvlxiZqzW6eIe4WbyFhbr2+8nYiBqqIgUuq20Zu0xWPExGp0odbEcaKEAgbwYAAKQgJAZ152xtAQUAg2JwabJ+tdnmpnJaY8wLMw7BS4AgT2oDqF6xRkVnyfZ0TjfW2IACt-bQWXU-T6A6l5h2DlW0KNtFvCf2ep5ZezjQlYxVLyCOqEXvhRENeqB2ct7guQvQrujbWz8W4in8-YDyuSbTjPa-t7uJ+QlXxXdT1tbqKuM7DTUXt3qip5K-y56b02YnNuSrZJ5s2warSmtdmwzGAZxEBEVgMAbAWNCDvkJvOBSUczZ2Qck5TcRgLawBvp9SYBEYp4G+HMBu+Ui4FkXlteIgCsE6D0HzHuwoXSAnSo5YEEYySGHkFfOMCZ9b4KKo3A0tZyqnlrNbJSH9GpAA