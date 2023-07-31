package it.dercole.prototypes.game_login.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import it.dercole.prototypes.game_login.model.PlayerAccount;


public interface PlayerAccountRepository extends ListCrudRepository<PlayerAccount, Long> {

	/**
	 * Convenience method to return a single player account by its username (JPA conventional query would return a list)
	 * @param username
	 * @return
	 */
	@Query("SELECT a FROM player_account a WHERE a.username = :username")
	public PlayerAccount getAccountByUsername(@Param("username") String username);
	
	/**
	 * Convenience method to return a single player account by its email (JPA conventional query would return a list)
	 * @param email
	 * @return
	 */
	@Query("SELECT a FROM player_account a WHERE a.email = :email")
	public PlayerAccount getAccountByEmail(@Param("email") String email);
	
	
}
