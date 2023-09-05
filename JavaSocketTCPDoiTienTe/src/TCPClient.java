import java.io.*;
import java.net.*;
import java.text.DecimalFormat;
import java.util.Scanner;

public class TCPClient {
    public static void main(String[] args) {
        try {
            Socket clientSocket = new Socket("localhost", 9000); 
            PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter the amount: ");
            double amountToSend = scanner.nextDouble();

            System.out.print("Enter the currency (USD/CAN): ");
            String currencyToSend = scanner.next();

            String messageToSend = amountToSend + "," + currencyToSend;

            outToServer.println(messageToSend); // Gửi dữ liệu đến máy chủ

            String serverResponse = inFromServer.readLine(); // Đọc phản hồi từ máy chủ
            double convertedAmount = Double.parseDouble(serverResponse);

            DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
            String formattedAmount = decimalFormat.format(convertedAmount);

            System.out.println("Received from server: " + formattedAmount + " VND");

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
