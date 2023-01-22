package engine

import engine.model.Quiz
import engine.model.QuizCompletion
import engine.model.User
import engine.model.UserAnswer
import engine.security.UserPrincipal
import engine.service.QuizCompletionService
import engine.service.QuizService
import engine.service.UserService
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.util.logging.Logger
import javax.validation.Valid
import javax.validation.constraints.Max
import javax.validation.constraints.Min


@RestController
//@Validated
@RequestMapping("/api")
class MainController (
    private val userService: UserService,
    private val quizService: QuizService,
    private val quizCompletionService: QuizCompletionService
) {

    private val logger: Logger = Logger.getLogger("logger")

    @PostMapping(value = ["/register"], consumes = ["application/json"])
    fun registerNewUser(@Valid @RequestBody user: User): ResponseEntity<String> {
        return try {
            userService.registerNewUser(user)
            ResponseEntity.ok().build()
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    @PostMapping(value = ["/quizzes"], consumes = ["application/json"])
    fun createQuiz(
        @RequestBody quiz: @Valid Quiz,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<Quiz> {
        return try {
            quiz.user = userPrincipal.user
            ResponseEntity.ok(quizService.add(quiz))
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    @GetMapping(value = ["/quizzes/{id}"])
    fun getQuiz(@PathVariable id: Long): Quiz {
        return quizService[id]
    }

    @GetMapping(value = ["/quizzes"])
    fun getAllQuizzes(
        @RequestParam(defaultValue = "0") page: @Min(0) Int,
        @RequestParam(defaultValue = "10") pageSize: @Min(10) @Max(30) Int,
        @RequestParam(defaultValue = "id") sortBy: String?
    ): Page<Quiz?> {
        return quizService.getAllQuizzes(page, pageSize, sortBy)
    }

    @PostMapping(value = ["/quizzes/{id}/solve"], consumes = ["application/json"])
    fun solveQuiz(
        @PathVariable id: Long,
        @RequestBody userAnswer: UserAnswer,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): SolveResponse {
        if (quizService.solve(id, userAnswer)) {
            val quizCompletion = QuizCompletion(
                userPrincipal.user.email,
                id,
                LocalDateTime.now(),
                userPrincipal.user,
                quizService[id]
            )
            quizCompletionService.add(quizCompletion)
            val solveResponse = SolveResponse(true, SolveResponse.CORRECT_ANSWER)
            logger.info("success ${solveResponse.success} feedback ${solveResponse.feedback}")
            return solveResponse
        } else {
            val solveResponse = SolveResponse(false, SolveResponse.WRONG_ANSWER)
            logger.info("success ${solveResponse.success} feedback ${solveResponse.feedback}")
            return solveResponse
        }
    }

    @DeleteMapping("/quizzes/{id}")
    fun delete(
        @PathVariable id: Long,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): ResponseEntity<String> {
        if (quizService[id].user.email
                .equals(userPrincipal.user.email)
        ) {
            quizService.delete(id)
            return ResponseEntity.noContent().build()
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build()
    }

    @GetMapping("/quizzes/completed")
    fun getUserCompletions(
        @RequestParam(defaultValue = "0") page: @Min(0) Int,
        @RequestParam(defaultValue = "10") pageSize: @Min(10) @Max(30) Int,
        @RequestParam(defaultValue = "completionDate") sortBy: String?,
        @AuthenticationPrincipal userPrincipal: UserPrincipal
    ): Page<QuizCompletion?>? {
        return quizCompletionService.getCompletions(
            userPrincipal.user.email,
            page,
            pageSize,
            sortBy
        )
    }
}