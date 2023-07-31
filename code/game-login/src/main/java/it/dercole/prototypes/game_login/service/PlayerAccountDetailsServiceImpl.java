package it.dercole.prototypes.game_login.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import it.dercole.prototypes.game_login.exception.AccountNotFoundException;
import it.dercole.prototypes.game_login.exception.AlreadyExistsException;
import it.dercole.prototypes.game_login.model.PlayerAccount;
import it.dercole.prototypes.game_login.model.PlayerAccountDetails;
import it.dercole.prototypes.game_login.dao.PlayerAccountRepository;

//Implementation for the interface used by Spring Security Authenticator
@Service
public class PlayerAccountDetailsServiceImpl implements UserDetailsService {

	private final PlayerAccountRepository playerAccountRepository;
	
	public PlayerAccountDetailsServiceImpl(PlayerAccountRepository playerAccountRepository) {
		this.playerAccountRepository = playerAccountRepository;	
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		PlayerAccount account = playerAccountRepository.getAccountByUsername(username);
		
		if (account == null) {
			throw new UsernameNotFoundException(String.format("Could not find account by username \"%s\".", username));
		}
		
		return new PlayerAccountDetails(account);
	}
	
	public UserDetails loadUserByEmail(String email) throws AccountNotFoundException {
		PlayerAccount account = playerAccountRepository.getAccountByEmail(email);
		
		if (account == null) {
			throw new AccountNotFoundException(String.format("Could not find account by email \"%s\".", email));
		}
		
		return new PlayerAccountDetails(account);
	}
	
	
	public UserDetails create(PlayerAccount account) throws AlreadyExistsException, IllegalArgumentException{
		Assert.isNull(account.getId(), "Account creation requires a null id (to let the DB generate it)");
		//Check if username and email are available, throws AlreadyExistsException if not
		checkAvailability(account);
		//Encode the password
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPw = encoder.encode(account.getPassword());
		account.setPassword(encodedPw);
		account.setPasswordConfirm(encodedPw);
		//set role to user (in case an attacker tries to create a privileged account)
		account.setRole("USER");
		
		PlayerAccount newlyCreatedAccount =  playerAccountRepository.save(account);
		return new PlayerAccountDetails(newlyCreatedAccount);
	}
	
	private void checkAvailability(PlayerAccount account) throws AlreadyExistsException{
		if(playerAccountRepository.getAccountByUsername(account.getUsername())!= null) {
			throw new AlreadyExistsException("There exists already an account using this username.");
		}
		if(playerAccountRepository.getAccountByEmail(account.getEmail()) != null) {
			throw new AlreadyExistsException("There exists already an account using this email.");
		}
	}

}
