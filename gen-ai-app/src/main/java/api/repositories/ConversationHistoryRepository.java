package api.repositories;

import api.model.ConversationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationHistoryRepository extends JpaRepository<ConversationHistory, Long> {
}

