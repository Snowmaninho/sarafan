package letscode.sarafan.config;

import letscode.sarafan.domain.User;
import letscode.sarafan.repo.UserDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

public class SarafanAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    UserDetailsRepo userDetailsRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken)authentication;
        OAuth2User oAuth2user = token.getPrincipal();
        Map<String, Object> attributes = oAuth2user.getAttributes();
        //attributes.forEach( (k,v) -> System.out.println(k + " " + v.toString()));

        String id = (String) attributes.get("sub");
        User user = userDetailsRepo.findById(id).orElseGet(() -> {
            User newUser = new User();
            newUser.setId(id);
            newUser.setName((String) attributes.get("name"));
            newUser.setEmail((String) attributes.get("email"));
            newUser.setGender((String) attributes.get("gender"));
            newUser.setLocale((String) attributes.get("locale"));
            newUser.setUserpic((String) attributes.get("picture"));

            return newUser;
        });

        user.setLastVisit(LocalDateTime.now());

        userDetailsRepo.save(user);
    }
}
