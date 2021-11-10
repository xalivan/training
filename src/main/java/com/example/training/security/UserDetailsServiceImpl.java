package com.example.training.security;

import com.example.training.model.UserOriginal;
import com.example.training.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository usersRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String originalLastName) throws UsernameNotFoundException {
        UserOriginal userOriginal = usersRepository.findOneByLastName(originalLastName).orElseThrow(IllegalArgumentException::new);
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userOriginal.getRole().name());
        return new User(userOriginal.getLastName(), userOriginal.getPassword(), Collections.singletonList(authority));
    }

}