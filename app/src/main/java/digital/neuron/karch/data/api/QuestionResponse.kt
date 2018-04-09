package digital.neuron.karch.data.api

import com.google.gson.annotations.SerializedName

import digital.neuron.karch.data.model.Question

class QuestionResponse {
    @SerializedName("items")
    var questions: List<Question>? = null
}
