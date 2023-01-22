package engine.service

import engine.model.QuizCompletion
import engine.repository.QuizCompletionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service


@Service
open class QuizCompletionService @Autowired constructor(private val quizCompletionRepository: QuizCompletionRepository) {

    fun add(quizCompletion: QuizCompletion): QuizCompletion {
        return quizCompletionRepository.save(quizCompletion)
    }

    fun getCompletions(
        userId: String?, pageNumber: Int,
        pageSize: Int, sortBy: String?
    ): Page<QuizCompletion?>? {
        val paging: Pageable = PageRequest.of(
            pageNumber,
            pageSize,
            Sort.by(sortBy).descending()
        )
        return quizCompletionRepository.findAll(userId, paging)
    }
}