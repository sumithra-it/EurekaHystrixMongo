package com.optimagrowth.oauth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

@Configuration
public class OAuth2Config extends AuthorizationServerConfigurerAdapter{

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired    
    private PasswordEncoder passwordEncoder;
	
	//Configure the ClientDetailsService, e.g. declaring individual clients and their properties.
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient("ritasinc").secret(passwordEncoder.encode("passwordkey"))
				.authorizedGrantTypes("refresh_token", "password", "client_credentials")
				.scopes("webclient", "mobilsclient");
	}

	//Configure the non-security features of the Authorization Server endpoints, like token store, token customizations, user approvals and grant types.
	//You shouldn't need to do anything by default, unless you need password grants, in which case you need to provide an AuthenticationManager.
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager).userDetailsService(userDetailsService);
	} 
	
}
