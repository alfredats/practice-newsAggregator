package visa.vttp.paf.practicenewsAggregator;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import visa.vttp.paf.practicenewsAggregator.repo.NewsRepo;
import visa.vttp.paf.practicenewsAggregator.service.UsersService;

@SpringBootTest
public class AuthServiceTest {

    @Autowired
    private UsersService uSvc;

    @MockBean
    private NewsRepo nRepo;

    @PostConstruct
    private void preventAPIcalls() {
        Mockito.when(nRepo.tableSize("articles")).thenReturn(30);
    }

    @Test
    private void shouldFailAuth() {
        String user = "mockFailure";
        String pass = "mockFailure";

        Mockito.when(nRepo.authUser(user, pass)).thenReturn(false);

        Assertions.assertFalse(uSvc.authUser(user, pass));
    }
    
    @Test
    private void shouldPassAuth() {
        String user = "mockSuccess";
        String pass = "mockSuccess";

        Mockito.when(nRepo.authUser(user, pass)).thenReturn(true);

        Assertions.assertTrue(uSvc.authUser(user, pass));
    }
    
}
