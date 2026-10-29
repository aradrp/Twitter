public class Test {
//    public class Main {
//        public static void main(String[] args) {
//            ServerSocket serverSocket = null;
//            try {
//                serverSocket = new ServerSocket(12345);
//                System.out.println("Server started. Waiting for clients...");
//                while (true) {
//                    Socket clientSocket = serverSocket.accept();
//                    System.out.println("Client connected: " + clientSocket.getInetAddress());
//                    new ServerThread(clientSocket).start();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    class ServerThread extends Thread {
//        private Socket clientSocket;
//
//        public ServerThread(Socket clientSocket) {
//            this.clientSocket = clientSocket;
//        }
//
//        @Override
//        public void run() {
//            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
//                 ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream())) {
//                // Your server logic goes here
//                // ...
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//    Here's what's changed:
//
//    I created a new ServerThread class that extends Thread.
//            In the main method, I create a new ServerSocket and accept incoming connections in a loop.
//    When a new client connects, I create a new ServerThread instance and pass the Socket object to its constructor.
//    I start the ServerThread using the start() method.
//    In the ServerThread class, I override the run() method, which is where your server logic goes. This is where you'll handle the client's requests and responses.
//    By using a separate thread for each client, you can handle multiple clients concurrently. Each thread will run independently, allowing multiple clients to connect and interact with the server simultaneously.
//
//    Note that you'll need to synchronize access to shared resources, such as the Client.accounts list, to ensure thread safety. You can use synchronization mechanisms like synchronized blocks or Lock objects to achieve this.
}
