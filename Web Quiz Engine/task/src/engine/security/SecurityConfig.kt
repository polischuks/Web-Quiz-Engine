package engine.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder


@Configuration
@EnableWebSecurity
open class SecurityConfig @Autowired constructor(private val userDetailsService: EngineUserDetailsService):
    WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(authProvider())
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.httpBasic()
            .and().authorizeRequests().antMatchers("/actuator/shutdown").permitAll()
            .antMatchers("/api/register").anonymous()
            .antMatchers("/h2-console/**").hasRole("ADMIN")
            .and().authorizeRequests()
            .anyRequest().authenticated()
            .and()
            .csrf().disable()
            .headers().frameOptions().disable()
    }

    @Bean
    open fun authProvider(): AuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailsService)
        authProvider.setPasswordEncoder(encoder())
        return authProvider
    }

    @Bean
    open fun encoder(): PasswordEncoder {
        return BCryptPasswordEncoder(11)
    }
}