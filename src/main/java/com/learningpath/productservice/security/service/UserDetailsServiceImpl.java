package com.learningpath.productservice.security.service;

import com.learningpath.productservice.model.User;
import com.learningpath.productservice.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         User user = userRepository.findByEmail(username);
         if (user!=null)
         {
             return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),user.getUserRoles());
         }
         else
             throw new UsernameNotFoundException("Given email is not registered service");
    }
}
