package engine.repository

import engine.model.QuizCompletion
import engine.model.QuizCompletionId
import org.springframework.data.domain.*
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository


@Repository
interface QuizCompletionRepository :
    PagingAndSortingRepository<QuizCompletion?, QuizCompletionId?> {
    @Query(value = "SELECT c FROM QuizCompletion c WHERE c.userId = :id")
    fun findAll(@Param("id") userId: String?, pageable: Pageable?): Page<QuizCompletion?>?
}