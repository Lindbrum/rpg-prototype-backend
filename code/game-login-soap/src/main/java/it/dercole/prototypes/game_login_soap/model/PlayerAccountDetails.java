package it.dercole.prototypes.game_login_soap.model;

import java.util.Collection;
import java.util.function.Function;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

//Implements the interface required by Spring Security
public class PlayerAccountDetails implements UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2678701143237600248L;
	
	
	private PlayerAccount account;

	public PlayerAccountDetails(PlayerAccount account) {
		this.account = account;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return account.getPassword();
	}

	@Override
	public String getUsername() {
		return account.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return isEnabled();
	}

	@Override
	public boolean isAccountNonLocked() {
		return isEnabled();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return account.getPasswordNonExpired();
	}

	@Override
	public boolean isEnabled() {
		return account.getEnabled();
	}
	
	public PlayerAccount getAccount() {
		return this.account;
	}
	
//	private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
//		Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
//		// Ensure array iteration order is predictable (as per
//		// UserDetails.getAuthorities() contract and SEC-717)
//		SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<>(new AuthorityComparator());
//		for (GrantedAuthority grantedAuthority : authorities) {
//			Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
//			sortedAuthorities.add(grantedAuthority);
//		}
//		return sortedAuthorities;
//	}
	
//	private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {
//
//		private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
//
//		@Override
//		public int compare(GrantedAuthority g1, GrantedAuthority g2) {
//			// Neither should ever be null as each entry is checked before adding it to
//			// the set. If the authority is null, it is a custom authority and should
//			// precede others.
//			if (g2.getAuthority() == null) {
//				return -1;
//			}
//			if (g1.getAuthority() == null) {
//				return 1;
//			}
//			return g1.getAuthority().compareTo(g2.getAuthority());
//		}
//
//	}
	
	/**
	 * Returns {@code true} if the supplied object is a {@code User} instance with the
	 * same {@code username} value.
	 * <p>
	 * In other words, the objects are equal if they have the same username, representing
	 * the same principal.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PlayerAccountDetails) {
			return this.getUsername().equals(((PlayerAccount) obj).getUsername());
		}
		return false;
	}

	/**
	 * Returns the hashcode of the {@code username}.
	 */
	@Override
	public int hashCode() {
		return this.getUsername().hashCode();
	}

	@Override
	public String toString() {
		return account.toString();
	}
	
	/**
	 * Creates a UserBuilder with a specified user name
	 * @param username the username to use
	 * @return the UserBuilder
	 */
	public static PlayerAccountBuilder withUsername(String username) {
		return builder().username(username);
	}

	/**
	 * Creates a UserBuilder
	 * @return the UserBuilder
	 */
	public static PlayerAccountBuilder builder() {
		return new PlayerAccountBuilder();
	}
	
	/**
	 * Builds the user to be added. At minimum the username, password, and role
	 * should provided. The remaining attributes have reasonable defaults.
	 */
	public static final class PlayerAccountBuilder {

		private String username;

		private String password;
		
		private String email;

		private String role;

		private boolean disabled;

		private boolean credentialsExpired;

		private Function<String, String> passwordEncoder = (password) -> new BCryptPasswordEncoder().encode(password);

		/**
		 * Creates a new instance
		 */
		private PlayerAccountBuilder() {
		}

		/**
		 * Populates the username. This attribute is required.
		 * @param username the username. Cannot be null.
		 * @return the {@link PlayerAccountBuilder} for method chaining (i.e. to populate
		 * additional attributes for this user)
		 */
		public PlayerAccountBuilder username(String username) {
			Assert.notNull(username, "username cannot be null");
			this.username = username;
			return this;
		}

		/**
		 * Populates the password. This attribute is required.
		 * @param password the password. Cannot be null.
		 * @return the {@link PlayerAccountBuilder} for method chaining (i.e. to populate
		 * additional attributes for this user)
		 */
		public PlayerAccountBuilder password(String password) {
			Assert.notNull(password, "password cannot be null");
			this.password = password;
			return this;
		}
		
		/**
		 * Populates the password. This attribute is required.
		 * @param password the password. Cannot be null.
		 * @return the {@link PlayerAccountBuilder} for method chaining (i.e. to populate
		 * additional attributes for this user)
		 */
		public PlayerAccountBuilder email(String email) {
			Assert.notNull(email, "email cannot be null");
			this.email = email;
			return this;
		}

		/**
		 * Encodes the current password (if non-null) and any future passwords supplied to
		 * {@link #password(String)}. Defaults to default BCrypt encoder.
		 * @param encoder the encoder to use
		 * @return the {@link PlayerAccountBuilder} for method chaining (i.e. to populate
		 * additional attributes for this user)
		 */
		public PlayerAccountBuilder passwordEncoder(Function<String, String> encoder) {
			Assert.notNull(encoder, "encoder cannot be null");
			this.passwordEncoder = encoder;
			return this;
		}

		/**
		 * Populates the role.
		 *
		 * <p>
		 * This attribute is required
		 * </p>
		 * @param roles the roles for this user (i.e. USER, ADMIN, etc). Cannot be null,
		 * contain null values or start with "ROLE_"
		 * @return the {@link PlayerAccountBuilder} for method chaining (i.e. to populate
		 * additional attributes for this user)
		 */
		public PlayerAccountBuilder role(String role) {
			Assert.isTrue(!role.isBlank(), "Role must be defined.");
			this.role = role;
			return this;
		}

		/**
		 * Populates the role. This attribute is required, but can also be populated with
		 * {@link #role(String)}.
		 * @param authority the authority for this user. Cannot be null, or contain
		 * null values
		 * @return the {@link PlayerAccountBuilder} for method chaining (i.e. to populate
		 * additional attributes for this user)
		 * @see #roles(String...)
		 */
		public PlayerAccountBuilder role(GrantedAuthority authority) {
			return role(authority.getAuthority());
		}

		/**
		 * Defines if the credentials are expired or not. Default is false.
		 * @param credentialsExpired true if the credentials are expired, false otherwise
		 * @return the {@link PlayerAccountBuilder} for method chaining (i.e. to populate
		 * additional attributes for this user)
		 */
		public PlayerAccountBuilder credentialsExpired(boolean credentialsExpired) {
			this.credentialsExpired = credentialsExpired;
			return this;
		}

		/**
		 * Defines if the account is disabled or not. Default is false.
		 * @param disabled true if the account is disabled, false otherwise
		 * @return the {@link PlayerAccountBuilder} for method chaining (i.e. to populate
		 * additional attributes for this user)
		 */
		public PlayerAccountBuilder disabled(boolean disabled) {
			this.disabled = disabled;
			return this;
		}

		public UserDetails build() {
			String encodedPassword = this.passwordEncoder.apply(this.password);
			PlayerAccount account = new PlayerAccount(null, this.username, encodedPassword, this.email, !this.disabled,
					!this.credentialsExpired, this.role);
			return new PlayerAccountDetails(account);
		}

	}

}
