package com.ge.digital.spo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (! "admin".equals(username)) {
			return null;
		}
		
		UserDetails user = User.withUsername("admin").password(passwordEncoder.encode("123456"))
				.roles("ADMIN", "USER")
				.build();
		
		return user;
	}

}
