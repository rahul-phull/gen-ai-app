package api.dto;

import api.model.Address;
import api.model.Provider;

public class ProviderWithAddressDTO {

    private Provider provider;
    private Address address;

    public ProviderWithAddressDTO() {
    }

    public ProviderWithAddressDTO(Provider provider, Address address) {
        this.provider = provider;
        this.address = address;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}

