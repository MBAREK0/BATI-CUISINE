package Repository;

import Entity.Quote;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

public interface QuoteRepository {

    Optional<Quote> save(Quote quote);
    Optional<Quote> findByProjectId(int id);
    Quote mapResultSetToQuote(ResultSet rs) throws Exception;
}