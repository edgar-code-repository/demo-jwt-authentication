package com.example.demo.principal;

import com.example.demo.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class UserPrincipal implements UserDetails {

	private static final long serialVersionUID = 1L;

	private String username;
    private String password;
    private boolean active;
    private List<GrantedAuthority> authorities;

    public UserPrincipal(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();

        if (user.getActive().equals("Y")) this.active = true;
        else this.active = false;

        this.authorities = Arrays.stream(user.getRoles().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
        return active;
    }    
	
}
