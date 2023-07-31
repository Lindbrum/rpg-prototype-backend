package it.dercole.prototypes.game_login.model.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = PasswordConfirmValidator.class)
@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfirmPassword {
    
	String message() default "Please confirm your password.";
    
	String passwordField() default "password";
	
	String passwordConfirmationField() default "passwordConfirm";
	
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
