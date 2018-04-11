# Socket

[![Gitter chat](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/areong_Socket/Lobby)

Threaded Java socket server and client

## Example

Create a server at port 5556 with a `MessageHandler`, which is the `EchoHandler` in this example.
``` java
SocketServer server = new SocketServer(5556, new EchoHandler());
```

Create a client connecting to localhost's port 5556.
``` java
SocketClient client = new SocketClient(InetAddress.getLocalHost(), 5556);
```

Send a message from the client.
``` java
client.println("Hello!");
```

Print out the message from the server. The function `SocketClient.readLine()` blocks.
``` java
System.out.println(client.readLine());
```

The `EchoHandler` implements the `MessageHandler` interface and overrides the abstract method `onReceive()`. The argument `connection` enables you to send a string back to the client.
``` java
public class EchoHandler implements MessageHandler {
    @Override
    public void onReceive(Connection connection, String message) {
        connection.send(message);
    }
}
```

## Features

`SocketServer` is threaded. It creates a thread for accepting connections and creates a new thread each time a new client is connected.
`SocketClient` is not threaded and the function `readLine()` blocks.
