package org.hotwax.hotwaxassignment.service;

import lombok.extern.slf4j.Slf4j;
import org.hotwax.hotwaxassignment.dto.request.LoginRequest;
import org.hotwax.hotwaxassignment.dto.request.SignupRequest;
import org.hotwax.hotwaxassignment.dto.response.AuthResponse;
import org.hotwax.hotwaxassignment.entity.Customer;
import org.hotwax.hotwaxassignment.exception.AuthenticationException;
import org.hotwax.hotwaxassignment.exception.BadRequestException;
import org.hotwax.hotwaxassignment.repository.CustomerRepository;
import org.hotwax.hotwaxassignment.util.JwtSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Slf4j
@Service
public class AuthService {

    private final CustomerRepository customerRepository;
    private final JwtSecurity jwtSecurity;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(CustomerRepository customerRepository, JwtSecurity jwtSecurity, BCryptPasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.jwtSecurity = jwtSecurity;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public AuthResponse signup(SignupRequest request) {
        if (customerRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BadRequestException("Email already exists");
        }

        Customer customer = new Customer();
        customer.setFirst_name(request.getFirstName());
        customer.setLast_name(request.getLastName());
        customer.setEmail(request.getEmail());
        customer.setPassword(passwordEncoder.encode(request.getPassword()));

        Customer savedCustomer = customerRepository.save(customer);

        String token = jwtSecurity.generateToken(savedCustomer.getEmail(), savedCustomer.getEmail());

        return new AuthResponse(
                token,
                savedCustomer.getEmail(),
                savedCustomer.getFirst_name(),
                savedCustomer.getLast_name(),
                "Registration successful"
        );
    }

    public AuthResponse login(LoginRequest request) {

        Customer customer = customerRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AuthenticationException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), customer.getPassword())) {
            throw new AuthenticationException("Invalid email or password");
        }

        String token = jwtSecurity.generateToken(customer.getEmail(), customer.getEmail());

        return new AuthResponse(
                token,
                customer.getEmail(),
                customer.getFirst_name(),
                customer.getLast_name(),
                "Login successful"
        );
    }
}

