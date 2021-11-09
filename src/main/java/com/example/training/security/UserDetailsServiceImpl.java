package com.example.training.security;

import com.example.training.model.User;
import com.example.training.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository usersRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String originalLastName) throws UsernameNotFoundException {
        Optional<User> user = usersRepository.findOneByLastName(originalLastName);
        if (user.isPresent()) {
            return new UserDetailsImpl(user.get());
        } else throw new IllegalArgumentException("User not found " + user.toString());
    }
}
