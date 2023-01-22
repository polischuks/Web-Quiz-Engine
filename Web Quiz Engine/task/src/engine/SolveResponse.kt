package engine

data class SolveResponse(
    val success: Boolean = false,
    val feedback: String? = null,
){
    companion object {
        const val CORRECT_ANSWER: String = "Congratulations, you're right!"
        const val WRONG_ANSWER: String = "Wrong answer! Please, try again."
    }
}