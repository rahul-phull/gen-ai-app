package api.model;

import java.util.List;

public class ConversationRequest {
    private Long providerId;
    private List<QuestionAnswer> questionAnswers;

    // Constructors, getters, and setters

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public List<QuestionAnswer> getQuestionAnswers() {
        return questionAnswers;
    }

    public void setQuestionAnswers(List<QuestionAnswer> questionAnswers) {
        this.questionAnswers = questionAnswers;
    }
}
