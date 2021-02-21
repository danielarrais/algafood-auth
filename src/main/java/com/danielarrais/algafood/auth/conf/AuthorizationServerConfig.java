package com.danielarrais.algafood.auth.conf;

import com.danielarrais.algafood.auth.conf.pkce.PkceAuthorizationCodeTokenGranter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Arrays;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("algafood-api")
                .secret(passwordEncoder.encode("web123"))
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("write", "read")
                .accessTokenValiditySeconds(60 * 60 * 6)
                .refreshTokenValiditySeconds(60 * 60 * 12)
                .and()
                .withClient("algafood-faturamento")
                .secret(passwordEncoder.encode("web123"))
                .authorizedGrantTypes("authorization_code")
                .redirectUris("http://localhost:8080")
                .scopes("write", "read")
                .and()
                .withClient("algafood-admin")
                .redirectUris("http://localhost:8080")
                .authorizedGrantTypes("implicit")
                .scopes("write", "read")
                .and()
                .withClient("algafood-analitics")
                .secret(passwordEncoder.encode("web123"))
                .authorizedGrantTypes("client_credentials")
                .scopes("read")
                .and()
                .withClient("refresh-token")
                .secret(passwordEncoder.encode("web123"));

    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.checkTokenAccess("permitAll()");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .reuseRefreshTokens(false)
                .accessTokenConverter(jwtAccessTokenConverter())
                .tokenGranter(tokenGranter(endpoints));
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey("algafood");

        return jwtAccessTokenConverter;
    }

    private TokenGranter tokenGranter(AuthorizationServerEndpointsConfigurer endpoints) {
        var pkceAuthorizationCodeTokenGranter = new PkceAuthorizationCodeTokenGranter(endpoints.getTokenServices(),
                endpoints.getAuthorizationCodeServices(), endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory());

        var granters = Arrays.asList(
                pkceAuthorizationCodeTokenGranter, endpoints.getTokenGranter());

        return new CompositeTokenGranter(granters);
    }
}

/*
Samples:
 Code verify sample: BWMNeSVjI90plZvQdxqFHUe_JbGHIaV4u6Wn4XFRJTU
 Code challenge sample: BWMNeSVjI90plZvQdxqFHUe_JbGHIaV4u6Wn4XFRJTU
 Code challenge s256 sample: Yq87qM2TQ2bdjRN6.twYJIPVKjW_Blu-GKMUJPe_xl4tafvy1ubpQMU~q~Pjb8Xc7MOhafK2i-0RU7Ja~uThma9U~bPMw8XC.guZMua56SBcrxsu4fAezsHqyz4FPxxQ

 http://localhost:8081/oauth/authorize?response_type=code&client_id=algafood-faturamento&state=abc&redirect_uri=http://localhost:8080&code_challenge=BWMNeSVjI90plZvQdxqFHUe_JbGHIaV4u6Wn4XFRJTU&code_challenge_method=s256
 http://localhost:8081/oauth/authorize?response_type=code&client_id=algafood-faturamento&state=abc&redirect_uri=http://localhost:8080&code_challenge=BWMNeSVjI90plZvQdxqFHUe_JbGHIaV4u6Wn4XFRJTU&code_challenge_method=plain
 http://localhost:8081/oauth/authorize?response_type=code&client_id=algafood-faturamento&state=abc&redirect_uri=http://localhost:8080
 http://localhost:8081/oauth/authorize?response_type=token&client_id=algafood-admin&state=abc&redirect_uri=http://localhost:8080
 */