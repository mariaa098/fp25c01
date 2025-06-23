import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String HOST = "mysql-ca7261b-mariaaarumm-b88a.b.aivencloud.com";
    private static final String PORT = "23795";
    private static final String DBNAME = "username_schema";
    private static final String USER = "avnadmin";
    private static final String PASS = "AVNS_6U60N7PNhw8VCEOa4Ry";
    private static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DBNAME + "?sslmode=require";

    private static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static void saveWinner(String winnerName) {
        if (winnerName.toLowerCase().contains("ai")) {
            System.out.println("AI winner, not saving to high scores.");
            return;
        }

        String sql = "INSERT INTO high_scores(winner_name) VALUES(?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, winnerName);
            pstmt.executeUpdate();
            System.out.println("Winner " + winnerName + " saved to database.");

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Database connection failure or error saving score.");
            e.printStackTrace();
        }
    }

    public static List<String> getHighScores() {
        List<String> scores = new ArrayList<>();
        String sql = "SELECT winner_name, COUNT(*) as wins FROM high_scores GROUP BY winner_name ORDER BY wins DESC LIMIT 10";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String name = rs.getString("winner_name");
                int wins = rs.getInt("wins");
                scores.add(String.format("%s - %d Kemenangan", name, wins));
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Database connection failure or error fetching scores.");
            e.printStackTrace();
            scores.add("Gagal memuat skor.");
        }
        return scores;
    }
}