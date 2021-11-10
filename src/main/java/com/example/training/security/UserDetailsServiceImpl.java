package com.example.training.security;

import com.example.training.model.UserEntity;
import com.example.training.repository.UserRepository;
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

    public UserDetailsServiceImpl(UserRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String originalLastName) throws UsernameNotFoundException {
        UserEntity userEntity = usersRepository.findOneByLastName(originalLastName).orElseThrow(IllegalArgumentException::new);
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userEntity.getRole().name());
        return new User(userEntity.getLastName(), userEntity.getPassword(), Collections.singletonList(authority));
    }

}