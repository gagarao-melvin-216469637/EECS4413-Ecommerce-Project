package org.o7planning.krispykart.service;

import java.util.ArrayList;
import java.util.List;

import org.o7planning.krispykart.dao.AccountDAO;
import org.o7planning.krispykart.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private AccountDAO accountDAO;

	/**
	 * returns a deep copy of the specified user via a username
	 * 
	 * @param String username
	 * @return UserDetails user details
	 * @throws UsernameNotFoundException no such username exists
	 */
	@Override
	public UserDetails loadUserByUsername(String user) throws UsernameNotFoundException {
		Account acc = accountDAO.findAccount(user);
		//System.out.println("Account = " + acc);

		// check for missing info
		if (acc == null)
			throw new UsernameNotFoundException("User " + user + " was not found in the database");

		// get role, access, and authority
		String role = acc.getUserRole();
		List<GrantedAuthority> grantedAccess = new ArrayList<GrantedAuthority>();
		GrantedAuthority auth = new SimpleGrantedAuthority(role);
		grantedAccess.add(auth);

		// check account expiration
		boolean accNotExpired = true;
		boolean credNotExpired = true;
		boolean accNotLocked = true;
		boolean active = acc.isActive();
		

		UserDetails details = (UserDetails) new User(acc.getUserName(), acc.getEncrytedPassword(), active,
				accNotExpired, credNotExpired, accNotLocked, grantedAccess);

		return details;
	}

}