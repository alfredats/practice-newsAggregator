package visa.vttp.paf.practicenewsAggregator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import visa.vttp.paf.practicenewsAggregator.repo.NewsRepo;


@Service
public class UsersService {

    @Autowired
    private NewsRepo nRepo;

    public boolean authUser(String user, String pass) {
        return nRepo.authUser(user, pass);
    }
    
    
}
