package api.controller;

import api.model.*;
import api.repositories.ConversationHistoryRepository;
import api.repositories.ConversationRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import api.service.JwtUtil;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class ConversationController {

    private final ConversationRepository conversationRepository;
    private final ConversationHistoryRepository conversationHistoryRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public ConversationController(ConversationRepository conversationRepository, ConversationHistoryRepository conversationHistoryRepository, JwtUtil jwtUtil) {
        this.conversationRepository = conversationRepository;
        this.conversationHistoryRepository = conversationHistoryRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/conversation")
    public ResponseEntity<Long> saveConversation(@RequestBody JsonNode conversationRequest,
                                                 @RequestHeader("Authorization") String token) {
        String userId = jwtUtil.getUserIdFromToken(token);

        Conversation conversation = new Conversation();
        conversation.setUserId(Long.valueOf(userId));
        conversation.setProviderId(conversationRequest.get("providerId").asLong());
        conversation.setSummary("");
        conversation.setCreatedOn(LocalDateTime.now());
        conversation.setStatus("Active");

        conversation = conversationRepository.save(conversation);


        List<QuestionAnswer> questionAnswers = new ArrayList<>();
        for (JsonNode qaNode : conversationRequest.get("questionAnswers")) {
            String question = qaNode.get("question").asText();
            String answer = qaNode.get("answer").asText();
            QuestionAnswer questionAnswer = new QuestionAnswer(question, answer);
            questionAnswers.add(questionAnswer);
        }
        for (QuestionAnswer qa : questionAnswers) {
            ConversationHistory conversationHistory = new ConversationHistory();
            conversationHistory.setUserId(Long.valueOf(userId));
            conversationHistory.setConversation(conversation);
            conversationHistory.setQuestion(qa.getQuestion());
            conversationHistory.setAnswer(qa.getAnswer());
            conversationHistory.setCreatedOn(LocalDateTime.now());
            conversationHistoryRepository.save(conversationHistory);
        }

        return ResponseEntity.ok(conversation.getId());
    }

    @PutMapping("/conversation/summary")
    public ResponseEntity<String> saveConversationSummary(@RequestBody JsonNode request) {
        Long conversationId = request.get("conversationId").asLong();
        String summary = request.get("summary").asText();

        Optional<Conversation> conversationOptional = conversationRepository.findById(conversationId);
        if (conversationOptional.isPresent()) {
            Conversation conversation = conversationOptional.get();
            conversation.setSummary(summary);
            conversationRepository.save(conversation);
            return ResponseEntity.ok("Summary saved successfully for conversation ID: " + conversationId);
        } else {
            return ResponseEntity.notFound().build();
        }

    }
}
