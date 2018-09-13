package com.example.demo.service.impl;

import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class AuthProviderServiceImpl implements AuthenticationProvider {

    @Autowired
    UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = (String) authentication.getPrincipal();
        String pwd = authentication.getCredentials().toString();

        if (userRepository.findByNameAndAndPassword(userName,pwd).isPresent()){
            List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ADMIN"));
            return new UsernamePasswordAuthenticationToken(userName, pwd, authorities);
        }else {
            throw new BadCredentialsException("Error username/password");
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(
                UsernamePasswordAuthenticationToken.class);
    }
}
