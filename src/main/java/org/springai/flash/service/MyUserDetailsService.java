package org.springai.flash.service;

import org.springai.flash.Model.UserPrincipal;
import org.springai.flash.Model.Users;
import org.springai.flash.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("User not Found");
        }

        return new UserPrincipal(user) ;
    }
}
