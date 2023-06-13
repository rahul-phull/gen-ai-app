package api.controller;

import api.dto.ProviderWithAddressDTO;
import api.model.Provider;
import api.service.JwtUtil;
import api.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class ProviderController {

    private final ProviderService providerService;
    private final JwtUtil jwtUtil;

    @Autowired
    public ProviderController(ProviderService providerService, JwtUtil jwtUtil) {
        this.providerService = providerService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/userProviders")
    public ResponseEntity<List<ProviderWithAddressDTO>> getProvidersForUser(@RequestHeader("Authorization") String token) {
        String userId = jwtUtil.getUserIdFromToken(token);
        if (userId == null) {
            // Invalid token or user not found
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<ProviderWithAddressDTO> providers = providerService.getProvidersWithAddressByUserId(Long.parseLong(userId));
        return ResponseEntity.ok(providers);
    }
}
