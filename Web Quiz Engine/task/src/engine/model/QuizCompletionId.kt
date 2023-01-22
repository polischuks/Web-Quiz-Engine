package engine.model

import java.io.Serializable
import java.time.LocalDateTime

class QuizCompletionId : Serializable {
    private val userId: String? = null
    private val quizId: Long = 0
    private val completionDate: LocalDateTime? = null

    companion object {
        private const val serialVersionUID = 8730599536453858883L
    }
}
