import java.net.*;
import java.text.DecimalFormat;

public class UDPServer {
    static double currencyUSD =23000;
    static double currencyCAD =17000;
    
    public static void main(String[] args) {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(9876); 

            System.out.println("Server is listening...");

            while (true) {
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

                socket.receive(receivePacket); // Nhận dữ liệu từ máy khách

                String clientMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());

                // Phân tích dữ liệu từ máy khách
                String[] parts = clientMessage.split(",");
                double amount = Double.parseDouble(parts[0]); // Số tiền
                String currency = parts[1]; // Đơn vị tiền tệ

                // Quy đổi ngoại tệ sang tiền Việt
                double convertedAmount = convertCurrency(amount, currency);

                // Định dạng kết quả với dấu phân cách hàng nghìn ","
                DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
                String formattedAmount = decimalFormat.format(convertedAmount);

                // Gửi kết quả về máy khách
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();
                byte[] sendData = formattedAmount.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                socket.send(sendPacket);

                System.out.println("Received from client: " + clientMessage);
                System.out.println("Sent to client: " + formattedAmount + " VND");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }

    private static double convertCurrency(double amount, String currency) {
        if (currency.equalsIgnoreCase("USD")) {
            return amount * currencyUSD;
        } else if (currency.equalsIgnoreCase("CAD")) {
            return amount * currencyCAD;
        }
        return 0;
    }
}
