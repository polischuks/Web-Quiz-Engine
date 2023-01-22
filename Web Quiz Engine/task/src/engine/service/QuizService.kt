package engine.service

import engine.exception.QuizNotFoundException
import engine.model.Quiz
import engine.model.UserAnswer
import engine.repository.QuizRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.*
import org.springframework.stereotype.Service
import java.util.logging.Logger


@Service
open class QuizService @Autowired constructor(private val quizRepository: QuizRepository) {
    private val logger = Logger.getLogger("QuizService")
    fun add(quiz: Quiz): Quiz {
        return quizRepository.save(quiz)
    }

    operator fun get(id: Long): Quiz {
        return quizRepository
            .findById(id)
            .orElseThrow { QuizNotFoundException() }!!
    }

    fun delete(id: Long) {
        if (quizRepository.existsById(id)) {
            quizRepository.deleteById(id)
            return
        }
        throw QuizNotFoundException()
    }

    fun solve(id: Long, userAnswer: UserAnswer): Boolean {
        return get(id).answer.toMutableList() == userAnswer.answer
    }

    fun getAllQuizzes(pageNumber: Int, pageSize: Int, sortBy: String?): Page<Quiz?> {
        val paging: Pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy))
        return quizRepository.findAll(paging)
    }
}