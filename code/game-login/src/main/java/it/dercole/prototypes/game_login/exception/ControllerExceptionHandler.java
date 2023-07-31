package it.dercole.prototypes.game_login.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import it.dercole.prototypes.game_login.model.HttpStatusData;

@RestControllerAdvice
public class ControllerExceptionHandler {

	// Exception thrown on malformed requests
	@ExceptionHandler(MalformedRequestException.class)
	public ResponseEntity<HttpStatusData> handleMalformedRequestException(MalformedRequestException ex) {
		// Return 400 - Bad request
		return new ResponseEntity<>(
				new HttpStatusData(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage()),
				HttpStatus.BAD_REQUEST);
	}

	// Exception thrown on duplicate username/email
	@ExceptionHandler(AlreadyExistsException.class)
	public ResponseEntity<HttpStatusData> handleAlreadyExistsException(AlreadyExistsException ex) {
		// Return 409 - Conflict
		return new ResponseEntity<>(
				new HttpStatusData(HttpStatus.CONFLICT, HttpStatus.CONFLICT.value(), ex.getLocalizedMessage()),
				HttpStatus.CONFLICT);
	}

	// Exception thrown on account not found
	@ExceptionHandler(AccountNotFoundException.class)
	public ResponseEntity<HttpStatusData> handleAccountNotFoundException(AccountNotFoundException ex) {
		// Return 404 - Not found
		return new ResponseEntity<>(
				new HttpStatusData(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), ex.getLocalizedMessage()),
				HttpStatus.NOT_FOUND);
	}

	// Exception thrown when a PathVariable can't be parsed
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<HttpStatusData> handleMethodArgumentTypeMismatchException(
			MethodArgumentTypeMismatchException ex) {
		// If this is caused by a number convertion exception, then it's from the
		// controller endpoint: return HTTP 400.
		if (ex.getCause() instanceof NumberFormatException) {
			return handleParsingException(new ParsingException("Invalid parameter value", ex));
		} else { // If not, then it's an internal exception: return HTTP 500
			return handleGenericException(ex);
		}
	}

	// ID parsing exception handler (usually called by the handler above)
	@ExceptionHandler(ParsingException.class)
	public ResponseEntity<HttpStatusData> handleParsingException(ParsingException ex) {
		return new ResponseEntity<>(
				new HttpStatusData(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage()),
				HttpStatus.BAD_REQUEST);
	}

	// Generic, catch-all handler.
	@ExceptionHandler(Throwable.class)
	public ResponseEntity<HttpStatusData> handleGenericException(Throwable ex) {
		ex.printStackTrace();
		return new ResponseEntity<>(new HttpStatusData(HttpStatus.INTERNAL_SERVER_ERROR,
				HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error has occured."),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
