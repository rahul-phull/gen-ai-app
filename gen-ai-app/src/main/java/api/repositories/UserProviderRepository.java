package api.repositories;

import api.model.UserProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProviderRepository extends JpaRepository<UserProvider, Long> {


    List<UserProvider> findUserProviderByUserId(Long userId);

    List<UserProvider> findByUserId(Long userId);
}
