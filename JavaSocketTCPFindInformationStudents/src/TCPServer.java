import java.io.*;
import java.net.*;
import java.sql.*;
import java.sql.DriverManager;


public class TCPServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9876);
            System.out.println("Server is running...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                // Xử lý yêu cầu từ client
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                String studentID = reader.readLine();

                // Truy vấn cơ sở dữ liệu
                String studentName = queryDatabase(studentID);

                // Gửi tên sinh viên về client
                writer.write(studentName);
                writer.newLine();
                writer.flush();

                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String queryDatabase(String studentID) {
        String jdbcURL = "jdbc:mysql://localhost:3306/student_db";
        String dbUser = "root";
        String dbPassword = "123";

        try {
            Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
            String query = "SELECT student_name FROM students WHERE student_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, studentID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("student_name");
            } else {
                return "Student not found";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error";
        }
    }
}
