import java.io.*;
import java.net.*;

public class TCPClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 9876);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            // Nhập mã số sinh viên từ người dùng
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter student ID: ");
            String studentID = userInput.readLine();

            // Gửi mã số sinh viên đến máy chủ
            writer.write(studentID);
            writer.newLine();
            writer.flush();

            // Nhận và in tên sinh viên từ máy chủ
            String studentName = reader.readLine();
            System.out.println("Student Name: " + studentName);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
