package visa.vttp.paf.practicenewsAggregator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.annotation.PostConstruct;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import visa.vttp.paf.practicenewsAggregator.model.Article;
import visa.vttp.paf.practicenewsAggregator.repo.NewsRepo;
import visa.vttp.paf.practicenewsAggregator.service.NewsService;

@SpringBootTest
public class NewsServiceTest {

    @Autowired
    private NewsService nSvc;

    @MockBean
    private NewsRepo nRepo;

    @PostConstruct
    private void preventAPIcalls() {
        Mockito.when(nRepo.tableSize("articles")).thenReturn(30);
    }

    @Test
    public void shouldReturn3Articles() {
        Integer limit = 3; 
        Integer offset = 0;
        List<Article> aList = Arrays.asList(new Article(), new Article(), new Article());
        Mockito.when(nRepo.getArticles(limit, offset)).thenReturn(aList);

        assertEquals(limit, nSvc.getAllArticles(limit, offset).size());
    }
    
}
