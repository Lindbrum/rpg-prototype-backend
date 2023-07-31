package it.dercole.prototypes.game_login_soap.model;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity(name = "player_account")
public class PlayerAccount implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 293067957709226447L;

	//private static final Logger LOGGER = LoggerFactory.getLogger(PlayerAccount.class);
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Username required.")
	@Length(max=45, message = "Max length for username exceeded (45 characters).")
	@Column(length = 45, nullable = false, unique = true)
	private String username;
	
	@NotBlank(message = "Password required.")
	@Length(min=60, max=60,  message = "Expected bcrypt encoded password string length is 60. Maybe the password isn't being encoded with BCrypt?")
	@Column(length = 60, nullable = false)
	private String password;
	
	@NotBlank(message = "Email required.")
	@Email( message = "Invalid email.")
	@Length(max = 100, message = "Max length for email exceeded (100 characters).")
	@Column(length = 100, nullable = false, unique = true)
	private String email;
	
	@NotNull
	@Column(nullable = false)
	private Boolean enabled;
	
	@NotNull
	@Column(name="password_ok", nullable = false)
	private Boolean passwordNonExpired;
	
	@NotNull
	@Pattern(regexp = "(USER|ADMIN)")
	@Column(name = "role", nullable = false)
	private String role;
	
	
	/**
	 * Simplified constructor, useful when registering new accounts (id is left @{code null}, both booleans are set to {@code true}, lastly the account is given the base user role).
	 * @param username cannot be null or blank.
	 * @param password cannot be null or blank.
	 * @param email cannot be null or blank.
	 */
	public PlayerAccount(
			@NotBlank(message = "Username required.") @Length(max = 45, message = "Max length for username exceeded (45 characters).") String username,
			@NotBlank(message = "Password required.") @Length(min = 1, max = 24, message = "Max length for password exceeded (24 characters).") String password,
			@NotBlank(message = "Email required.") @Email(message = "Invalid email.") @Length(max = 100, message = "Max length for email exceeded (100 characters).") String email) {
		this(null, username, password, email, true, true, "USER");
	}
	
	
	





	public PlayerAccount(Long id,
			@NotBlank(message = "Username required.") @Length(max = 45, message = "Max length for username exceeded (45 characters).") String username,
			@NotBlank(message = "Password required.") @Length(min = 1, max = 24, message = "Max length for password exceeded (24 characters).") String password,
			@NotBlank(message = "Email required.") @Email(message = "Invalid email.") @Length(max = 100, message = "Max length for email exceeded (100 characters).") String email,
			@NotNull Boolean enabled, @NotNull Boolean passwordNonExpired,
			@NotNull @Pattern(regexp = "(USER|ADMIN)") String role) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.enabled = enabled;
		this.passwordNonExpired = passwordNonExpired;
		this.role = role;
	}








	//Required by Hibernate (also why i can't define final fields)
	public PlayerAccount() {}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}

	

	public Boolean getEnabled() {
		return enabled;
	}



	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	



	public Boolean getPasswordNonExpired() {
		return passwordNonExpired;
	}


	public void setPasswordNonExpired(Boolean passwordNonExpired) {
		this.passwordNonExpired = passwordNonExpired;
	}


	



	public String getRole() {
		return role;
	}








	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * Returns {@code true} if the supplied object is a {@code User} instance with the
	 * same {@code username} value.
	 * <p>
	 * In other words, the objects are equal if they have the same username, representing
	 * the same principal.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PlayerAccount) {
			return this.username.equals(((PlayerAccount) obj).username);
		}
		return false;
	}

	/**
	 * Returns the hashcode of the {@code username}.
	 */
	@Override
	public int hashCode() {
		return this.username.hashCode();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getName()).append(" [");
		sb.append("Username=").append(this.username).append(", ");
		sb.append("Password=[PROTECTED], ");
		sb.append("Email=").append(this.email).append(", ");
		sb.append("Enabled=").append(this.enabled).append(", ");
		sb.append("credentialsNonExpired=").append(this.passwordNonExpired).append(", ");
		sb.append("Granted Authorities=").append(this.role).append("]");
		return sb.toString();
	}

}
