import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class TCPServer {
    private static Map<String, Double> exchangeRates = new HashMap<>();

    static {
        exchangeRates.put("USD", 23000.0);
        exchangeRates.put("CAN", 17200.0);
        // Thêm các tỷ giá chuyển đổi khác ở đây
    }

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9000); 
            System.out.println("Server is listening...");

            while (true) {
                Socket clientSocket = serverSocket.accept(); // Chấp nhận kết nối từ client
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter outToClient = new PrintWriter(clientSocket.getOutputStream(), true);

                String clientMessage = inFromClient.readLine(); // Đọc dữ liệu từ client
                System.out.println("Received from client: " + clientMessage);

                // Phân tích dữ liệu từ client
                String[] parts = clientMessage.split(",");
                double amount = Double.parseDouble(parts[0]); // Số tiền
                String currency = parts[1]; // Đơn vị tiền tệ

                // Quy đổi ngoại tệ sang tiền Việt
                double convertedAmount = convertCurrency(amount, currency);

                outToClient.println(convertedAmount); // Gửi kết quả về client

                clientSocket.close();
                System.out.println("Connection closed.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static double convertCurrency(double amount, String currency) {
        if (exchangeRates.containsKey(currency.toUpperCase())) {
            return amount * exchangeRates.get(currency.toUpperCase());
        }
        return 0;
    }
}
