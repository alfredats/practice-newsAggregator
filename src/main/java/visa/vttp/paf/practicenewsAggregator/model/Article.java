package visa.vttp.paf.practicenewsAggregator.model;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.JsonObject;

public class Article {
    private Integer articleId;
    private String source;
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlImage;
    private Timestamp timestamp;

    private static SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    /**
     * Creation of Article objects, given information received from MySQL database
     * @param sqlRowSet
     * @return
     */
    public static Article create(SqlRowSet sqlRowSet) {
        Article a = new Article();
        a.setArticleId(sqlRowSet.getInt("article_id"));
        a.setSource(sqlRowSet.getString("source_name"));
        a.setAuthor(sqlRowSet.getString("author"));
        a.setTitle(sqlRowSet.getString("title"));
        a.setDescription(sqlRowSet.getString("description"));
        a.setUrl(sqlRowSet.getString("url"));
        a.setUrlImage(sqlRowSet.getString("urlImage"));
        a.setTimestamp(sqlRowSet.getTimestamp("published_at"));
        return a;
    }

    /**
     * This is a method for creation of Article objects for uploading to the MySQL DB.
     * @param json - JSON Object containing article details, from NewsAPI
     * @return Article object (minus article_id, which is assigned on the database)
     * @throws ParseException
     */
    public static Article create(JsonObject json) throws ParseException {
        Article a = new Article();
        a.setSource(json.getJsonObject("source").getString("name"));
        a.setTitle(json.getString("title"));
        Timestamp t = new Timestamp(
            dateParser.parse(json.getString("publishedAt"))
                      .getTime()
        );
        a.setTimestamp(t);

        if (!json.isNull("author")) { a.setAuthor(json.getString("author")); }
        if (!json.isNull("description")) { a.setDescription(json.getString("description")); }
        if (!json.isNull("url")) { a.setUrl(json.getString("url")); }
        if (!json.isNull("urlToImage")) { a.setUrlImage(json.getString("urlToImage")); }

        return a;
    }

    @Override
    public String toString() {
        return "Article [author=" + author + ", description=" + description
                + ", timestamp=" + timestamp + ", title=" + title + ", url=" + url + ", urlImage=" + urlImage + "]";
    }

    /**
     * @return String return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return String return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return String return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return String return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return String return the urlImage
     */
    public String getUrlImage() {
        return urlImage;
    }

    /**
     * @param urlImage the urlImage to set
     */
    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    /**
     * @return Timestamp return the timestamp
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }


    /**
     * @return String return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(String source) {
        this.source = source;
    }


    /**
     * @return Integer return the articleId
     */
    public Integer getArticleId() {
        return articleId;
    }

    /**
     * @param articleId the articleId to set
     */
    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

}
