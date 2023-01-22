package engine.service

import engine.exception.UserAlreadyExistsException
import engine.exception.UserNotFoundException
import engine.model.User
import engine.repository.UserRepository
import engine.security.Authority
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService @Autowired constructor(
    var userRepository: UserRepository, 
    var passwordEncoder: PasswordEncoder
) {

    fun registerNewUser(user: User): User {
        if (userRepository.existsById(user.email)) {
            throw UserAlreadyExistsException()
        }
        val newUser = User()
        newUser.email = user.email
        newUser.password = passwordEncoder.encode(user.password)
        newUser.authority = Authority.USER
        return userRepository.save(newUser)
    }

    operator fun get(email: String): User {
        return userRepository
            .findById(email)
            .orElseThrow { UserNotFoundException() }!!
    }
}