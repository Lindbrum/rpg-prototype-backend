package it.dercole.prototypes.game_login_soap.service;

import java.util.List;

import it.dercole.prototypes.game_login_soap.exception.AccountNotFoundException;
import it.dercole.prototypes.game_login_soap.exception.AlreadyExistsException;
import it.dercole.prototypes.game_login_soap.model.PlayerAccount;

/**
 * Service accessing player account data
 */
public interface PlayerAccountService {

	/**
	 * Dump the entire content of the player data database table
	 * @return a list containing all player account records
	 */
	List<PlayerAccount> dump();
	
	/**
	 * Lookup an account.
	 * @param usernameOrEmail email or username.
	 * @return the account matching the given email or username.
	 * @throws AccountNotFoundException if not found.
	 */
	PlayerAccount lookup(String usernameOrEmail) throws AccountNotFoundException;
	
	/**
	 * Do a lookup using the given username
	 * @param username
	 * @return the player account matching this username, if any.
	 * @throws AccountNotFoundException if not found.
	 */
	PlayerAccount findByUsername(String username) throws AccountNotFoundException;
	
	/**
	 * Do a lookup using the given email
	 * @param email
	 * @return the player account matching this email, if any.
	 * @throws AccountNotFoundException if not found.
	 */
	PlayerAccount findByEmail(String email) throws AccountNotFoundException;
	
	/**
	 * Create a new player account.
	 * @param newAccount object containing the new account data
	 * @return the new account data, including the database generated ID.
	 * @throws AlreadyExistsException if either username or email are already taken.
	 */
	PlayerAccount create(PlayerAccount newAccount) throws AlreadyExistsException;
	
	/**
	 * Update a player account.
	 * @param updatedAccount object containing the updated account data
	 * @return the updated account data.
	 * @throws AlreadyExistsException if either username or email are already taken.
	 * @throws IllegalArgumentException if ID is null
	 */
	PlayerAccount update(PlayerAccount updatedAccount) throws AlreadyExistsException, IllegalArgumentException;
	
	/**
	 * Soft deletes a player account. Soft deleting will mark the database record as inactive without deleting it.
	 * @param deletedAccount object containing the to be deleted account data.
	 * @return the deleted account data
	 * @throws IllegalArgumentException If ID is null, or this account was already soft deleted.
	 */
	PlayerAccount softDelete(PlayerAccount deletedAccount) throws IllegalArgumentException;
	
	/**
	 * Hard deletes a player account.
	 * @param deletedAccount object containing the to be deleted account data.
	 * @return the deleted account data
	 * @throws AccountNotFoundException if the account to delete is not found.
	 * @throws IllegalArgumentException If ID is null
	 */
	PlayerAccount hardDelete(PlayerAccount deletedAccount) throws AccountNotFoundException, IllegalArgumentException;
	
	
}
