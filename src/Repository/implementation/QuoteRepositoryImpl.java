package Repository.implementation;

import Database.DatabaseConnection;
import Entity.Project;
import Entity.Quote;
import Repository.QuoteRepository;

import java.sql.*;
import java.util.Optional;

public class QuoteRepositoryImpl implements QuoteRepository {

    private Connection connection;

    public QuoteRepositoryImpl() {
        this.connection = DatabaseConnection.getConnection();
    }
    @Override
    public Optional<Quote> save(Quote quote) {
        String query = "INSERT INTO Quotes (project_id, estimated_amount, issue_date, validity_date, accepted) VALUES (?, ?, ?, ?, ?) RETURNING quote_id";

        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            // Set parameters
            ps.setInt(1, quote.getProjectId());
            ps.setDouble(2, quote.getEstimatedAmount());
            ps.setDate(3, java.sql.Date.valueOf(quote.getIssueDate()));
            ps.setDate(4, java.sql.Date.valueOf(quote.getValidityDate()));
            ps.setBoolean(5, quote.isAccepted());

            // Execute the update
            int affectedRows = ps.executeUpdate();

            // Check if the insertion was successful
            if (affectedRows == 1) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        quote.setQuoteId(rs.getInt(1)); // Set the generated ID
                        return Optional.of(quote);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Log or handle the exception as needed
        }
        return Optional.empty();
    }
    @Override
    public Optional<Quote> findByProjectId(int project_id) {
        String query = "SELECT * FROM Quotes WHERE project_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, project_id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToQuote(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Log or handle the exception as needed
        }
        return Optional.empty();
    }
    @Override
    public Quote mapResultSetToQuote(ResultSet rs) throws SQLException {
        int quoteId = rs.getInt("quote_id");
        int projectId = rs.getInt("project_id");
        double estimatedAmount = rs.getDouble("estimated_amount");
        Date issueDate = rs.getDate("issue_date");
        Date validityDate = rs.getDate("validity_date");
        boolean accepted = rs.getBoolean("accepted");
        //public Quote(int projectId, double estimatedAmount, LocalDate issueDate, LocalDate validityDate, boolean accepted) {
        Quote quote = new Quote(projectId, estimatedAmount, issueDate.toLocalDate(), validityDate.toLocalDate(), accepted);
        quote.setQuoteId(quoteId);
        return quote;
    }

}
