package visa.vttp.paf.practicenewsAggregator.repo;

public interface Queries {
    public static final String SQL_VERIFY_USER = "SELECT COUNT(*) FROM users WHERE user_name = ? AND user_pass = SHA1(?);";
    public static final String SQL_ARTICLES_COUNT = "SELECT COUNT(*) FROM articles;";
    public static final String SQL_PUBLISHERS_COUNT = "SELECT COUNT(*) FROM publishers;";
    public static final String SQL_SELECT_ARTICLES = "SELECT * FROM articles LIMIT ? OFFSET ?;";
    public static final String SQL_SELECT_PUBLISHER_BY_NAME = "SELECT * FROM publishers WHERE source_name = ?;";

    public static final String SQL_INSERT_PUBLISHER= """
        INSERT INTO publishers(
            source_name,
            api_id
        ) VALUES
            (?,?);
            """;

    public static final String SQL_INSERT_ARTICLE = """
        INSERT INTO articles(
            author,
            title,
            description,
            url,
            urlImage,
            published_at,
            source_name
        ) VALUES 
            (?,?,?,?,?,?,?);
            """;
    
}
