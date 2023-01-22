package engine.model

import com.fasterxml.jackson.annotation.JsonIgnore
import engine.security.Authority
import javax.persistence.*
import javax.validation.constraints.*

@Entity
class User {
    @Id
    @NotNull
    @Email(regexp = "\\w+@\\w+\\.\\w+")
    lateinit var email: String

    @NotBlank
    @Size(min = 5, max = 255)
    lateinit var password: String

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])
    val quizzes: Set<Quiz>? = null

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    lateinit var authority: Authority

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])
    val completions: Set<QuizCompletion>? = null
}