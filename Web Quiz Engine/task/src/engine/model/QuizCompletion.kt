package engine.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime
import javax.persistence.*


@Entity
@IdClass(QuizCompletionId::class)
data class QuizCompletion (
    @JsonIgnore
    @Id
    @Column(name = "USER_ID")
    val userId: String? = null,

    @JsonProperty("id")
    @Id
    @Column(name = "QUIZ_ID")
    val quizId: Long = 0,

    @JsonProperty("completedAt")
    @Id
    val completionDate: LocalDateTime? = null,

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "USER_ID", insertable = false, updatable = false)
    val user: User? = null,

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "QUIZ_ID", insertable = false, updatable = false)
    val quiz: Quiz? = null
)