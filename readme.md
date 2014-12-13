# Socket
Threaded Java socket server and client

## Example

Create a server at port 5556 with a `MessageHandler`, which is the `EchoHandler` in this example.
```
SocketServer server = new SocketServer(5556, new EchoHandler());
```

Create a client connecting to localhost's port 5556.
```
SocketClient client = new SocketClient(InetAddress.getLocalHost(), 5556);
```

Send a message from the client.
```
client.println("Hello!");
```

Print out the message from the server. The function `SocketClient.readLine()` blocks.
```
System.out.println(client.readLine());
```

## Features

`SocketServer` is threaded. It creates a thread for accepting connections and creates a new thread when a client is connected.
`SocketClient` is not threaded and the function `readLine()` blocks.

## Current Issues

* 2014.12.13.
Do not have methods to close connections from `SocketServer` nor `SocketClient`. Connection does not close until the program exits. Using `Socket.close()` and `ServerSocket.close()` does not work elegantly.