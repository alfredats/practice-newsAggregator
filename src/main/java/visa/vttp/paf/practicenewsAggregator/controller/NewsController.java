package visa.vttp.paf.practicenewsAggregator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import visa.vttp.paf.practicenewsAggregator.model.Article;
import visa.vttp.paf.practicenewsAggregator.service.NewsService;

@RestController
@RequestMapping("/site")
public class NewsController {

    @Autowired
    private NewsService nSvc;
    
    @GetMapping(path = {"","/", "/index"})
    public ModelAndView indexEndpoint(
        @RequestParam(defaultValue = "10") String limit,
        @RequestParam(defaultValue = "0") String offset
    ) {
        final ModelAndView mav = new ModelAndView("site.html");
        Integer parsedLimit = Integer.parseInt(limit);
        Integer parsedOffset = Integer.parseInt(offset);
        parsedOffset = (parsedOffset >= 0) ? parsedOffset : 0;
        List<Article> artLst = nSvc.getAllArticles(parsedLimit, parsedOffset);
        
        mav.addObject("articles", artLst);

        return mav;
    }

    
}
