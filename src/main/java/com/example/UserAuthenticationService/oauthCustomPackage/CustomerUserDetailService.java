package com.example.UserAuthenticationService.oauthCustomPackage;

import com.example.UserAuthenticationService.models.User;
import com.example.UserAuthenticationService.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomerUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmailId(username).orElseThrow(() -> new UsernameNotFoundException("User does not exits"));
       return new CustomUserDetails(user);
    }
}
