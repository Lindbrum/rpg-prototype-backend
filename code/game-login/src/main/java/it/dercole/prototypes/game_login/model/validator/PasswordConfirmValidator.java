package it.dercole.prototypes.game_login.model.validator;

import org.springframework.beans.BeanWrapperImpl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordConfirmValidator implements ConstraintValidator<ConfirmPassword, Object> {

	String password;

	String passwordMatch;

	public PasswordConfirmValidator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(ConfirmPassword constraintAnnotation) {
		this.password = constraintAnnotation.passwordField();
		this.passwordMatch = constraintAnnotation.passwordConfirmationField();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		Object passwordValue = new BeanWrapperImpl(value).getPropertyValue(password);
		Object passwordMatchValue = new BeanWrapperImpl(value).getPropertyValue(passwordMatch);

		if (passwordValue != null) {
			return passwordValue.equals(passwordMatchValue);
		} else {
			return passwordMatchValue == null;
		}
	}

}
