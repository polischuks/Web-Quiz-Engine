package engine.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size


//@JsonIgnoreProperties(value = ["answer"])
//@JsonFilter("filterAnswer")
@Entity
@Table(name = "quizdb")
class Quiz {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0

    val title: @NotBlank String? = null

    val text: @NotBlank String? = null

    @NotNull
    @ElementCollection
    @CollectionTable(name = "QUIZ_OPTIONS", joinColumns = [JoinColumn(name = "QUIZ_ID")])
    @Column(name = "QUIZ_OPTION")
    val options: @Size(min = 2) MutableList<String>? = null

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ElementCollection
    @CollectionTable(name = "QUIZ_ANSWERS", joinColumns = [JoinColumn(name = "QUIZ_ID")])
    @Column(name = "QUIZ_ANSWER")
    var answer: MutableList<Int> = mutableListOf()

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = true)
    lateinit var user: User

    @JsonIgnore
    @OneToMany(mappedBy = "quiz", cascade = [CascadeType.ALL])
    val completions: MutableList<QuizCompletion> = mutableListOf()
}