package engine.model

import com.fasterxml.jackson.annotation.JsonProperty

data class UserAnswer(
    @JsonProperty("answer")
    val answer: MutableList<Int>
    )
