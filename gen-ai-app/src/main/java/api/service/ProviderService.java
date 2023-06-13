package api.service;

import api.dto.ProviderWithAddressDTO;
import api.model.Address;
import api.model.Provider;
import api.model.ProviderAddress;
import api.model.UserProvider;
import api.repositories.ProviderAddressRepository;
import api.repositories.UserProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProviderService {

    private final UserProviderRepository userProviderRepository;
    private final ProviderAddressRepository providerAddressRepository;

    @Autowired
    public ProviderService(UserProviderRepository userProviderRepository, ProviderAddressRepository providerAddressRepository) {
        this.userProviderRepository = userProviderRepository;
        this.providerAddressRepository = providerAddressRepository;
    }

    public List<ProviderWithAddressDTO> getProvidersWithAddressByUserId(Long userId) {
        List<UserProvider> userProviders = userProviderRepository.findByUserId(userId);

        List<ProviderWithAddressDTO> providersWithAddress = new ArrayList<>();
        for (UserProvider userProvider : userProviders) {
            Provider provider = userProvider.getProvider();
            ProviderAddress providerAddress = providerAddressRepository.findByProviderId(provider.getId());
            Address address = providerAddress.getAddress();

            ProviderWithAddressDTO providerWithAddress = new ProviderWithAddressDTO();
            providerWithAddress.setProvider(provider);
            providerWithAddress.setAddress(address);

            providersWithAddress.add(providerWithAddress);
        }

        return providersWithAddress;
    }

}
