package api.repositories;

import api.model.ProviderAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProviderAddressRepository extends JpaRepository<ProviderAddress, Long> {

    ProviderAddress findByProviderId(Long providerId);
}
