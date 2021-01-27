package com.kissheart.community.community.Contrller;

import com.kissheart.community.community.dto.GithubUser;
import com.kissheart.community.community.dto.accessTokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.kissheart.community.community.Provider.githubProvider;

import javax.servlet.http.HttpServletRequest;

@Controller
public class authorizeController {
    @Autowired
    private githubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request){

        accessTokenDto accessTokenDto = new accessTokenDto();
        accessTokenDto.setClient_id(clientId);
        accessTokenDto.setClient_secret(clientSecret);
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_uri(redirectUri);
        accessTokenDto.setState(state);



        String accessToken = githubProvider.getAccessToken(accessTokenDto);
        GithubUser user = githubProvider.getUser(accessToken);

        System.out.println(accessTokenDto);
        System.out.println(accessToken);
        System.out.println(user.getName());
        if(user != null){
            request.getSession().setAttribute("user",user);
            return "redirect:/";
        }
        else {
            return "redirect:/";
        }
    }
}
