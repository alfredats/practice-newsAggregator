package visa.vttp.paf.practicenewsAggregator.repo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import visa.vttp.paf.practicenewsAggregator.model.Article;
import visa.vttp.paf.practicenewsAggregator.model.Publisher;

import static visa.vttp.paf.practicenewsAggregator.repo.Queries.SQL_VERIFY_USER;
import static visa.vttp.paf.practicenewsAggregator.repo.Queries.SQL_SELECT_ARTICLES;
import static visa.vttp.paf.practicenewsAggregator.repo.Queries.SQL_SELECT_PUBLISHER_BY_NAME;
import static visa.vttp.paf.practicenewsAggregator.repo.Queries.SQL_INSERT_ARTICLE;
import static visa.vttp.paf.practicenewsAggregator.repo.Queries.SQL_INSERT_PUBLISHER;
import static visa.vttp.paf.practicenewsAggregator.repo.Queries.SQL_ARTICLES_COUNT;
import static visa.vttp.paf.practicenewsAggregator.repo.Queries.SQL_PUBLISHERS_COUNT;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class NewsRepo {
    private static final Logger logger = LoggerFactory.getLogger(NewsRepo.class);

    @Autowired
    JdbcTemplate jt;

    public boolean insertPublisher(Publisher s) {
        final int rows = jt.update(SQL_INSERT_PUBLISHER, 
            s.getSourceName(),
            s.getApiID()
        );
        return 1 == rows;
    }

    public boolean insertArticle(Article a)  {
        final int rows = jt.update(SQL_INSERT_ARTICLE, 
            a.getAuthor(),
            a.getTitle(),
            a.getDescription(),
            a.getUrl(),
            a.getUrlImage(),
            a.getTimestamp(),
            a.getSource()
        );
        return 1 == rows;
    }

    public Integer tableSize(String table) {
        String query = null;
        if (table.equals("articles")) {
            query = SQL_ARTICLES_COUNT;
        } else if (table.equals("publishers")) {
            query = SQL_PUBLISHERS_COUNT;
        }
        final SqlRowSet size = jt.queryForRowSet(query);
        if (!size.next()) {
            return -1;
        }
        return size.getInt(1);
    }

    public Optional<Publisher> getPublisherByName(String name) {
        final SqlRowSet result = jt.queryForRowSet(SQL_SELECT_PUBLISHER_BY_NAME, name);
        if (!result.next()) {
            return Optional.empty();
        }
        return Optional.ofNullable(Publisher.create(result));
    }

    public List<Article> getArticles(Integer limit, Integer offset) {
        List<Article> la = new ArrayList<>();
        final SqlRowSet articles = jt.queryForRowSet(SQL_SELECT_ARTICLES, limit, offset);
        while (articles.next()) {
            Article a = Article.create(articles);
            la.add(a);
        }
        return la;
    }

    public boolean authUser(String user, String pass) {
        return jt.queryForRowSet(SQL_VERIFY_USER, user, pass).next();
    }
}


