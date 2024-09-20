package Service;

import Entity.Quote;
import Repository.implementation.QuoteRepositoryImpl;

import java.util.Optional;

public class QuoteService {
    private QuoteRepositoryImpl quoteRepository = new QuoteRepositoryImpl();

    // ------------------- Quote CRUD -------------------
    public Optional<Quote> save(Quote quote){

        return quoteRepository.save(quote);
    }
    public Optional<Quote> findByProjectId(int project_id){
        return quoteRepository.findByProjectId(project_id);
    }
    public Optional<Quote> update(Quote quote){
        return quoteRepository.update(quote);
    }
    public void delete(int project_id){
         quoteRepository.delete(project_id);
    }

}
