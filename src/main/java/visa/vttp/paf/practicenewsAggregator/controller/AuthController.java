package visa.vttp.paf.practicenewsAggregator.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import visa.vttp.paf.practicenewsAggregator.service.UsersService;

@Controller
@RequestMapping(path={"/authenticate"})
public class AuthController {
    
    @Autowired
    private UsersService uSvc;

    @GetMapping("/logout")
    public String logoutResource(HttpSession sess) {
        sess.invalidate();
        return "index.html";
    }

    @PostMapping
    public ModelAndView postAuth(
        @RequestBody MultiValueMap<String, String> payload,
        HttpSession sess
    ) {
        ModelAndView mav;

        String user = payload.getFirst("username");
        String pass = payload.getFirst("password");

        if(uSvc.authUser(user, pass)) {
            mav = new ModelAndView("redirect:/site");
            sess.setAttribute("authExpiry", System.currentTimeMillis() + 15*60*1000);
            return mav;
        }
        
        mav = new ModelAndView("index.html");
        mav.setStatus(HttpStatus.FORBIDDEN);
        return mav;
    }


}
