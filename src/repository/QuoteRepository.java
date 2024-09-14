package repository;

import Entity.Quote;
import java.util.List;
import java.util.Optional;

public interface QuoteRepository {
    Optional<Quote> findById(int id);
    List<Quote> findByProjectId(int projectId);
    List<Quote> findAll();
    void save(Quote quote);
    void update(Quote quote);
    void delete(int id);
}