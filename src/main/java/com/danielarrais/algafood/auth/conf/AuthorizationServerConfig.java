package com.danielarrais.algafood.auth.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

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
                .withClient("refresh-token")
                .secret(passwordEncoder.encode("web123"));

    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.checkTokenAccess("isAuthenticated");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .reuseRefreshTokens(false);
    }
}
// http://localhost:8081/oauth/authorize?response_type=code&client_id=algafood-faturamento&state=abc&redirect_uri=http://localhost:8080
// http://localhost:8081/oauth/authorize?response_type=token&client_id=algafood-admin&state=abc&redirect_uri=http://localhost:8080