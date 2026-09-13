# 🎨 Java Online Whiteboard

A real-time collaborative online whiteboard application developed using Java. Multiple users can connect to a central server and draw on a shared digital whiteboard over a network.

## 📌 Project Overview

The Java Online Whiteboard uses a Client-Server architecture to provide real-time communication between multiple users.

When a user draws on the whiteboard, the drawing information is sent to the server. The server then broadcasts the information to all connected clients, allowing everyone to see the drawing in real time.

## ✨ Features

- Real-time collaborative drawing
- Multiple client connections
- TCP socket communication
- Adjustable brush size
- Color selection
- Eraser function
- Clear whiteboard option
- Real-time synchronization
- Graphical user interface

## 🛠️ Technologies Used

- Java
- Java Swing
- Java AWT
- Java Socket Programming
- TCP/IP
- Multithreading
- Object Serialization

## 🏗️ System Architecture

Client 1 ──┐
           │
Client 2 ──┼──> Whiteboard Server ──> All Connected Clients
           │
Client 3 ──┘

## ⚙️ How It Works

1. The Whiteboard Server starts and listens for client connections.
2. Multiple clients connect to the server through TCP sockets.
3. When a user draws on the whiteboard, the drawing coordinates and settings are converted into a message.
4. The message is sent to the server.
5. The server broadcasts the message to the connected clients.
6. Each client receives the message and displays the drawing on its whiteboard.
7. This allows multiple users to collaborate in real time.

## 📂 Project Components

### Message.java
Stores the drawing information that is transferred between the client and server.

### WhiteboardServer.java
Creates the server, accepts client connections, receives messages, and broadcasts them to connected clients.

### WhiteboardClient.java
Provides the graphical whiteboard interface and sends drawing information to the server.

## 🚀 How to Run

### Step 1: Clone the Repository

git clone https://github.com/gurusaeth12/JAVA-ONLINE-WHITEBOARD-.git

### Step 2: Start the Server

Run the `WhiteboardServer.java` file.

The server uses port:
5000

### Step 3: Start the Client

Run the `WhiteboardClient.java` file.

Enter the server IP address when requested.

For the same computer, use:
localhost

For computers connected to the same network, use the server computer's local IP address.

## 🎯 Learning Outcomes

* Understanding Client-Server architecture
* Java GUI development
* TCP socket programming
* Multithreading
* Object serialization
* Network communication
* Real-time data synchronization
* Event-driven programming

## 🔮 Future Improvements

* User authentication
* User names
* Chat functionality
* Undo and redo
* Save whiteboard as an image
* Private whiteboard rooms
* Improved connection management
* Persistent whiteboard sessions

## 👨‍💻 Author

Gurusaeth

GitHub: [https://github.com/gurusaeth12](https://github.com/gurusaeth12)

⭐ If you find this project useful, consider giving it a star!

```
```
