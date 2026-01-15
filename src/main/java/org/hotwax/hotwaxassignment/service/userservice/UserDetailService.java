package org.hotwax.hotwaxassignment.service.userservice;


import lombok.SneakyThrows;
import org.hotwax.hotwaxassignment.entity.Customer;
import org.hotwax.hotwaxassignment.repository.CustomerRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService {


    private final CustomerRepository customerRepository;

    public UserDetailService(CustomerRepository userRepository) {
        this.customerRepository = userRepository;
    }

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        Optional<Customer> customer = customerRepository.findByEmail(username);
        customer.orElseThrow(ChangeSetPersister.NotFoundException::new);
        return new UserDetail(customer.get());
    }
}