package socket;

import java.io.*;
import java.net.*;

public class TCPServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9000); 
            System.out.println("Server is listening...");

            while (true) {
                Socket clientSocket = serverSocket.accept(); 
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter outToClient = new PrintWriter(clientSocket.getOutputStream(), true);

                String clientMessage = inFromClient.readLine(); 
                System.out.println("Received from client: " + clientMessage);

                String capitalizedMessage = capitalizeWords(clientMessage);

                outToClient.println(capitalizedMessage); 

                clientSocket.close();
                System.out.println("Connection closed.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String capitalizeWords(String input) {
        String[] words = input.split("\\s+");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            result.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
        }

        return result.toString().trim();
    }
}

