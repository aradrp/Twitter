# Twitter — Java Social Media Application

A desktop social media application inspired by Twitter, developed as a university project using Java Swing and socket-based client-server communication.

The application provides a local social networking experience where users can create accounts, sign in, publish posts, explore public posts, like content, and save posts for later.

The project demonstrates the implementation of a graphical desktop application, client-server architecture, user authentication, and local data persistence.

## Features

### User Authentication
- User registration with account information
- Login using email and password
- Username and email validation
- Password hashing with PBKDF2
- Local account management

### Posts
- Create and publish posts
- Support for posts up to 280 characters
- Publish public or private posts
- Control whether other users can save posts
- View public posts in the timeline

### User Interactions
- Like posts
- Save posts for later
- View your own posts in the profile section
- Access saved and liked content

### Desktop User Interface
- Java Swing-based graphical interface
- Multiple application screens
- Client-server communication through Java sockets
- Support for multiple client instances connected to the server

## Technologies Used

- **Programming Language:** Java
- **GUI Framework:** Java Swing
- **Networking:** Java Sockets
- **Data Persistence:** Local files and Java serialization
- **Authentication:** PBKDF2 password hashing
- **Build and Runtime:** JDK 21
- **Testing:** Java integration smoke test

## Architecture

The application follows a client-server architecture.

```text
+--------------------+
|   Java Swing UI   |
|      Client       |
+--------------------+
          |
          | Java Sockets
          | Object Streams
          |
          v
+--------------------+
|   Java Server     |
| Authentication    |
| Request Handling  |
| Business Logic    |
+--------------------+
          |
          v
+--------------------+
| Local File Store |
| Accounts & Posts  |
| Likes & Saves    |
+--------------------+
```

### Client
The client is responsible for:
- Displaying the graphical user interface
- Collecting user input
- Sending requests to the server
- Displaying server responses

### Server
The server is responsible for:
- Handling client connections
- Processing authentication requests
- Managing posts and user interactions
- Reading and writing application data

### Data Storage
Application data is stored locally on the server using files and Java serialization.

## Project Structure

```text
twitter/
├── Client/
│   ├── src/
│   │   ├── Client.java
│   │   ├── Main.java
│   │   ├── MainFrame.java
│   │   ├── MenuFrame.java
│   │   └── TweetsAndreplies.java
│   │
│   ├── pics/
│   └── build/
│
├── Server/
│   ├── src/
│   │   ├── Main.java
│   │   ├── Client.java
│   │   ├── CredentialStore.java
│   │   └── TweetsAndreplies.java
│   │
│   ├── SAVES/
│   └── build/
│
├── tests/
│   └── IntegrationSmoke.java
│
├── run-server.ps1
├── run-client.ps1
├── test.ps1
├── .gitignore
└── README.md
```

## Requirements

Before running the application, make sure you have:

- JDK 21 installed
- Java added to your system PATH
- Windows PowerShell

Verify your Java installation:

```powershell
java -version
javac -version
```

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/aradrp/twitter.git
```

Navigate to the project directory:

```bash
cd twitter
```

### 2. Start the Server

Open a PowerShell terminal in the project directory and run:

```powershell
.\run-server.ps1
```

The server listens on:

```text
127.0.0.1:12345
```

Keep the server terminal open while using the application.

### 3. Start the Client

Open a second PowerShell terminal in the project directory and run:

```powershell
.\run-client.ps1
```

The application window should open.

### 4. Create an Account

Use the registration screen to create a local account.

After registration, sign in using your email and password.

You can then start publishing posts and exploring the application's features.

## Running Multiple Clients

The application supports multiple client instances connected to the same local server.

To test interactions between users:

1. Start the server.
2. Launch the client in one terminal.
3. Launch another client instance.
4. Register or sign in using different accounts.
5. Publish posts and test likes, saves, and post visibility.

## Testing

The repository includes an integration smoke test to verify the main client-server functionality.

With the server running, open another PowerShell terminal and execute:

```powershell
.\test.ps1
```

The test checks essential operations, including:

- Account registration
- User authentication
- Publishing public and private posts
- Timeline access
- Like functionality
- Save functionality
- Private post visibility

Test data is stored locally in the server's `SAVES` directory.

## Current Limitations

This project is an educational desktop application and is not intended for production use.

Some features are unfinished or disabled, including:

- Search
- Notifications
- Settings
- Following functionality

Additional limitations:

- The application uses local file-based storage rather than a database.
- The server is configured to accept connections only from the local machine.
- The timeline uses time-of-day sorting rather than a complete post creation timestamp.
- The user interface is designed for desktop displays and uses a fixed-width layout.
- The application does not provide production-grade security or deployment infrastructure.

## Learning Objectives

This project demonstrates practical experience with:

- Object-oriented programming in Java
- Java Swing GUI development
- Client-server application architecture
- Socket programming and object streams
- User authentication and password hashing
- Local file-based data persistence
- Request-response communication
- Integration testing
- Building a multi-user desktop application

## Academic Context

This project was developed as part of university coursework to explore the design and implementation of a client-server social media application.

It represents an educational implementation of core social networking concepts using Java.

## Author

**Arad Razpour**

GitHub: [@aradrp](https://github.com/aradrp)

