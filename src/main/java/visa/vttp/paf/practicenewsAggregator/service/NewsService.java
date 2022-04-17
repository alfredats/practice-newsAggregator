package visa.vttp.paf.practicenewsAggregator.service;

import java.io.StringReader;
import java.text.ParseException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import visa.vttp.paf.practicenewsAggregator.model.Article;
import visa.vttp.paf.practicenewsAggregator.model.Publisher;
import visa.vttp.paf.practicenewsAggregator.repo.NewsRepo;

@Service
public class NewsService {
    private static final Logger logger = LoggerFactory.getLogger(NewsService.class);

    @Autowired
    private NewsRepo aRepo;
    
    @PostConstruct
    private void init() throws Exception {
        if (aRepo.tableSize("articles") <= 0) {
            this.populateOnInit();
        }
        logger.info(">>> DB TABLE SIZE: articles -> " + aRepo.tableSize("articles"));
        logger.info(">>> DB TABLE SIZE: publishers -> " + aRepo.tableSize("publishers"));
        return;
    }

    /** 
     * Calls NewsAPI, converts results to appropriate POJO models, then inserts them into the respective databases
     * @throws Exception
     */
    public void populateOnInit() throws Exception {
        final String apiKey = System.getenv("API_KEY");
        final String apiRoot = "https://newsapi.org";
        final String everythingEndpoint = "/v2/top-headlines";

        String url = UriComponentsBuilder.fromUriString(apiRoot + everythingEndpoint)
                        .queryParam("country","sg")
                        .queryParam("language", "en")
                        .queryParam("pageSize", 100)
                        .queryParam("page", 1)
                        .toUriString();
    
        RequestEntity<Void> req = RequestEntity.get(url)
                                    .header("X-Api-Key", apiKey)
                                    .build();
        
        ResponseEntity<String> resp = new RestTemplate().exchange(req, String.class);
        if (resp.getStatusCodeValue() != 200) {
            throw new Exception("DB Init Error: Cannot get articles from CMS");
        }
        JsonObject body = Json.createReader(new StringReader(resp.getBody())).readObject();
        if (!body.getString("status").equals("ok")) {
            throw new Exception("DB Init Error: Cannot get articles from CMS");
        }

        JsonArray articles = body.getJsonArray("articles");
        articles.stream()
            .filter(v -> v != null)
            .map(v -> v.asJsonObject())
            .forEach((JsonObject v) -> {
                try {
                    Publisher s = Publisher.create(v.getJsonObject("source"));
                    if(this.addPublisher(s) == 0) {
                        logger.error(">>> FAILED TO ADD PUBLISHER: " + s.toString());
                    }

                    Article a = Article.create(v);
                    if(!aRepo.insertArticle(a)){
                        logger.error(">>> ARTICLE NOT RECORDED: " + a.toString());
                    } else {
                        logger.info(">>> %s - %s".formatted(a.getSource(), a.getTitle()));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            });
    }

    /**
     * Adds a Publisher to news database. 
     *  
     * @param p - Publisher object
     * @return 1 if successful, 0 if failure, -1 if publisher with same name already exists
     */
    public int addPublisher(Publisher p) {
        if (!aRepo.getPublisherByName(p.getSourceName()).isEmpty()){
            return -1;
        } else if (aRepo.insertPublisher(p)) {
            return 1;
        }   
        return 0;
    }

    public List<Article> getAllArticles(Integer limit, Integer offset) {
        return aRepo.getArticles(limit, offset);
    }

}
