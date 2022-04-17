package visa.vttp.paf.practicenewsAggregator.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.JsonObject;

public class Publisher {
    private String apiID;
    private String sourceName;

    private static Logger logger = LoggerFactory.getLogger(Publisher.class);

    public static Publisher create(SqlRowSet sqlRowSet) {
        Publisher s = new Publisher();
        s.setSourceName(sqlRowSet.getString("source_name"));
        s.setApiID(sqlRowSet.getString("api_id"));
        return s;
    }

    public static Publisher create(JsonObject json) {
        Publisher s = new Publisher();
        if (!json.isNull("id")) { s.setApiID(json.getString("id")); }
        s.setSourceName(json.getString("name"));
        return s;
    }

    @Override
    public String toString() {
        return "Publisher [apiID=" + apiID + ", sourceName=" + sourceName + "]";
    }

    /**
     * @return String return the apiID
     */
    public String getApiID() {
        return apiID;
    }

    /**
     * @return String return the sourceName
     */
    public String getSourceName() {
        return sourceName;
    }


    /**
     * @param apiID the apiID to set
     */
    public void setApiID(String apiID) {
        this.apiID = apiID;
    }

    /**
     * @param sourceName the sourceName to set
     */
    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

}