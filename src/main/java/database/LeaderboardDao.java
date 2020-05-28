package database;

import dataclasses.LeaderboardItem;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class LeaderboardDao {

    private final transient String errorMessage = "Could not connect to database: ";
    private DataSource dataSource;

    public LeaderboardDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Retrieves from the database list of top 10 scores (highest score for each user).
     * @return List of LeaderboardItem
     */
    public List<LeaderboardItem> getTop10Scores() throws Exception {
        try (PreparedStatement statement = this.dataSource.getConnection().prepareStatement(
                "SELECT username, MAX(score) as highest_score FROM "
                        + "(scores JOIN users ON scores.user_id = users.user_id) "
                        + "GROUP BY scores.user_id ORDER BY highest_score DESC LIMIT 10;")) {
            try (ResultSet result = statement.executeQuery()) {
                if (!result.first()) {
                    return new ArrayList<>();
                }
                List<LeaderboardItem> top10 = new ArrayList<>();
                while (result.next()) {
                    String username = result.getString(1);
                    int highestScore = result.getInt(2);
                    top10.add(new LeaderboardItem(username, highestScore));
                }
                return top10;
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
            throw new Exception(errorMessage + e.getMessage());
        }
    }
}
