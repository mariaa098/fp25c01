import java.sql.*;
import java.util.List;
import java.util.ArrayList;


public class DatabaseHelper {
    private static final String DB_URL = "jdbc:mysql://mysql-ca7261b-mariaaarumm-b88a.b.aivencloud.com:23795/username_schema?useSSL=true&requireSSL=true&serverTimezone=UTC";

    private static final String DB_USER = "avnadmin";
    private static final String DB_PASS = "AVNS_6U60N7PNhw8VCEOa4Ry";

    // Method untuk insert nama player ke database
    public static void insertPlayers(String player1, String player2) {
        String query = "INSERT INTO players (player1, player2) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, player1);
            stmt.setString(2, player2);
            stmt.executeUpdate();

            System.out.println("Data pemain berhasil dimasukkan ke database.");
        } catch (SQLException e) {
            System.err.println("Gagal menyimpan ke database:");
            e.printStackTrace();
        }
    }
    public static void insertHistory(String player1, String player2) {
        String sql = "INSERT INTO players (player1, player2) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, player1);
            stmt.setString(2, player2);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> getHistoryList() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT player1, player2 FROM players ORDER BY id DESC LIMIT 5";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String entry = rs.getString("player1") + " vs " +
                        rs.getString("player2");
                list.add(entry);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}