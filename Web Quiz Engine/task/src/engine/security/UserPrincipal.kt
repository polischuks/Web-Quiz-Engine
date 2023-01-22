package engine.security

import engine.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


class UserPrincipal(val user: User) : UserDetails {
    override fun getAuthorities(): List<GrantedAuthority> {
        return listOf<GrantedAuthority>(
            SimpleGrantedAuthority(
                user.authority.name
            )
        )
    }

    override fun getPassword(): String {
        return user.password
    }

    override fun getUsername(): String? {
        return user.email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}