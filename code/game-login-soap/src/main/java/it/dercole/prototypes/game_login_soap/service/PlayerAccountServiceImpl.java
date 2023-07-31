package it.dercole.prototypes.game_login_soap.service;

import java.util.List;

import org.springframework.stereotype.Service;

import it.dercole.prototypes.game_login_soap.exception.AccountNotFoundException;
import it.dercole.prototypes.game_login_soap.exception.AlreadyExistsException;
import it.dercole.prototypes.game_login_soap.model.PlayerAccount;

@Service
public class PlayerAccountServiceImpl implements PlayerAccountService {

	public PlayerAccountServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<PlayerAccount> dump() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	@Override
	public PlayerAccount lookup(String usernameOrEmail) throws AccountNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayerAccount findByUsername(String username) throws AccountNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayerAccount findByEmail(String email) throws AccountNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayerAccount create(PlayerAccount newAccount) throws AlreadyExistsException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayerAccount update(PlayerAccount updatedAccount) throws AlreadyExistsException, IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayerAccount softDelete(PlayerAccount deletedAccount) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayerAccount hardDelete(PlayerAccount deletedAccount)
			throws AccountNotFoundException, IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

}
