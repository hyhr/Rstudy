package com.r.study.oauth.server.authentication.social.config;

import com.r.study.oauth.server.authentication.social.QQ;
import com.r.study.oauth.server.authentication.social.QQImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.context.annotation.RequestScope;

/**
 * date 2021-08-02 15:40
 *
 * @author HeYiHui
 **/
@Configuration
public class SocialConfig {

    @Bean
    @RequestScope
    public QQ qq(OAuth2AuthorizedClientService clientService) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        String accessToken = null;
        String appId = null;
        if (authentication.getClass().isAssignableFrom(OAuth2AuthenticationToken.class)) {
            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
            String clientRegistrationId = oauthToken.getAuthorizedClientRegistrationId();
            if (clientRegistrationId.equals("qq")) {
                OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(clientRegistrationId, oauthToken.getName());
                accessToken = client.getAccessToken().getTokenValue();
                appId = client.getClientRegistration().getClientId();
            }
        }
        return new QQImpl(accessToken, appId);
    }

}
