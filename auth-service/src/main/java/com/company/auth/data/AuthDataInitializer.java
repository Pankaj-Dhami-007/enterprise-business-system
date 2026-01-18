package com.company.auth.data;

import com.company.auth.entity.Client;
import com.company.auth.entity.UserAuth;
import com.company.auth.repository.ClientRepository;
import com.company.auth.repository.UserAuthRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile({"local", "dev"})
@RequiredArgsConstructor
public class AuthDataInitializer implements CommandLineRunner {

    private final ClientRepository clientRepository;
    private final UserAuthRepository userAuthRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        log.info("Starting Auth data initialization");

        // 1️⃣ CLIENT
        Client client = clientRepository
                .findByName("DHAMI_CORP")
                .orElseGet(() -> {
                    Client c = new Client();
                    c.setName("DHAMI_CORP");
                    c.setClientSecret("dhami-secret-123");
                    c.setDescription("Local test client");
                    c.setIsActive(true);

                    log.info("Creating default client: {}", c.getName());
                    return clientRepository.save(c);
                });

        // 2️⃣ USER
        userAuthRepository
                .findByUsername("admin")
                .orElseGet(() -> {

                    UserAuth user = new UserAuth();
                    user.setId(1001L);
                    user.setUsername("admin");
                    user.setEmail("admin@acme.com");
                    user.setPassword(
                            passwordEncoder.encode("admin@123")
                    );
                    user.setIsActive(true);
                    user.setClient(client);

                    log.info("Creating default admin user");
                    return userAuthRepository.save(user);
                });

        log.info("Auth data initialization completed");
    }

}
