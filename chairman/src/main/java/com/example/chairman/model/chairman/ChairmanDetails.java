package com.example.chairman.model.chairman;

import com.example.chairman.entity.Chairman;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ChairmanDetails implements UserDetails {
    private Chairman chairman;
    private boolean enabled = true;

    public ChairmanDetails(Chairman chairman) {
        this.chairman = chairman;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_CHAIRMAN"));
        return authorities;
    }

    @Override
    public String getPassword() {
        return chairman.getPassword();
    }

    @Override
    public String getUsername() {
        return chairman.getUsername();
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
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getFullName(){
        return chairman.getLastName()+" "+chairman.getFirstName()+" "+chairman.getMiddleName();
    }
    public String getAvatar(){
        return chairman.getAvatar();
    }
    public Long getId(){return chairman.getId();}
}
