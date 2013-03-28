package com.seenukarthi.spring.security.kerberos.localhost;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;



public class LocalhostAuthenticationProvider implements AuthenticationProvider,
		InitializingBean {

	private UserDetailsService userDetailsService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		if(userDetailsService == null) throw new SecurityException("property userDetailsService is null");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.authentication.AuthenticationProvider#
	 * authenticate(org.springframework.security.core.Authentication)
	 */
	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		LocalhostAuthenticationToken auth = (LocalhostAuthenticationToken) authentication;
		String username = auth.getName();
		UserDetails userDetails = this.userDetailsService
				.loadUserByUsername(username);
		LocalhostAuthenticationToken output = new LocalhostAuthenticationToken(
				userDetails, userDetails.getAuthorities());
		return output;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.authentication.AuthenticationProvider#supports
	 * (java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return (LocalhostAuthenticationToken.class.isAssignableFrom(authentication));
	}

	public void setUserDetailsService(UserDetailsService detailsService) {
		this.userDetailsService = detailsService;
	}
}
