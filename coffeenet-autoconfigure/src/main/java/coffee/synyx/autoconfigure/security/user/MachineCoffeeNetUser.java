package coffee.synyx.autoconfigure.security.user;

import org.springframework.security.core.GrantedAuthority;

import org.springframework.util.Assert;

import java.util.Collection;


/**
 * Machine user implementation for {@link CoffeeNetUserDetails}.
 *
 * @author  David Schilling - schilling@synyx.de
 */
public final class MachineCoffeeNetUser implements CoffeeNetUserDetails {

    private static final long serialVersionUID = -2951903502295199963L;

    private final String username;
    private final Collection<GrantedAuthority> authorities;

    /**
     * @param  username  Must not be null.
     * @param  authorities  Must not be null.
     */
    public MachineCoffeeNetUser(String username, Collection<GrantedAuthority> authorities) {

        Assert.notNull(username);
        Assert.notNull(authorities);

        this.username = username;
        this.authorities = authorities;
    }

    @Override
    public String getUsername() {

        return username;
    }


    @Override
    public String getEmail() {

        throw new UnsupportedOperationException();
    }


    @Override
    public Collection<GrantedAuthority> getAuthorities() {

        return authorities;
    }


    @Override
    public boolean isAdmin() {

        return false;
    }


    @Override
    public boolean isMachineUser() {

        return true;
    }
}
