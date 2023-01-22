package engine.security

import engine.repository.UserRepository
import engine.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service


@Service
class EngineUserDetailsService @Autowired constructor(private val userRepository: UserRepository):
    UserDetailsService {
    //private lateinit var userService: UserService
    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findById(email).get()
        //val user = userService[email]
        return UserPrincipal(user)
    }
}