package it.dercole.prototypes.game_login.exception;

public class WrongPasswordException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3482491876493379704L;

	public WrongPasswordException() {
		// TODO Auto-generated constructor stub
	}

	public WrongPasswordException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public WrongPasswordException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public WrongPasswordException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public WrongPasswordException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
