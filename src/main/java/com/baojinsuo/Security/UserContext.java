package com.baojinsuo.Security;

import com.baojinsuo.model.Customer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/** This object wraps {@link Customer} and makes it {@link UserDetails} so that Spring Security can use it. */
public class UserContext implements UserDetails {

	private Customer customer;

	public UserContext(@NotNull final Customer customer) {
		this.customer = customer;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		for (String role : customer.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		return customer.getPassword();
	}

	@Override
	public String getUsername() {
		return customer.getUsername();
	}

	public Customer getCustomer() {
		return customer;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		return this == o
			|| o != null && o instanceof UserContext
			&& Objects.equals(customer, ((UserContext) o).customer);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(customer);
	}

	@Override
	public String toString() {
		return "UserContext{" +
			"customer=" + customer +
			'}';
	}
}
