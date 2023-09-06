import java.net.*;
import java.util.Scanner;

public class UDPClient {
    public static void main(String[] args) {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(); // Tự động chọn cổng
            InetAddress serverAddress = InetAddress.getByName("localhost"); 

            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter the amount: ");
            String amountInput = scanner.next();

            // Loại bỏ dấu phân cách hàng nghìn "," nếu có
            String cleanedAmountInput = amountInput.replace(",", "");

            double amountToSend = Double.parseDouble(cleanedAmountInput);

            System.out.print("Enter the currency (USD/CAD): ");
            String currencyToSend = scanner.next();

            String messageToSend = amountToSend + "," + currencyToSend;
            byte[] sendData = messageToSend.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, 9876);

            socket.send(sendPacket); // Gửi dữ liệu đến máy chủ

            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            socket.receive(receivePacket); // Nhận kết quả từ máy chủ

            String serverResponse = new String(receivePacket.getData(), 0, receivePacket.getLength());
            
            // Hiển thị kết quả như mong muốn
            System.out.println("Received from server: " + serverResponse);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }
}
