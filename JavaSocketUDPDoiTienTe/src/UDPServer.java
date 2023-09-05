import java.net.*;
// ...
import java.text.DecimalFormat;

public class UDPServer {
    public static void main(String[] args) {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(9876); // Mở cổng 9876 cho máy chủ

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
        // Tỉ giá cố định (ví dụ: 1 USD = 23000 VND)
        if (currency.equalsIgnoreCase("USD")) {
            return amount * 23000;
        } else if (currency.equalsIgnoreCase("EUR")) {
            return amount * 27000;
        }
        // Thêm các tỷ giá chuyển đổi khác ở đây

        return 0;
    }
}
