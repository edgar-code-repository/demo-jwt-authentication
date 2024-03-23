package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.principal.UserPrincipal;
import com.example.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("[UserDetailsServiceImpl][loadUserByUsername][BEGIN][username: " + username + "]");

        Optional<User> optionalUser = userRepository.findByUsername(username);
        optionalUser.orElseThrow(() -> new UsernameNotFoundException("No user found with username: " + username));

        UserPrincipal userPrincipal = new UserPrincipal(optionalUser.get());

        //Password: pwd = "$2a$10$MuuDu7M9Zsw3CsWrODoUFOFcfi1fNoQ2mtdPzdiK7QLYMqErExX46"

        /*UserPrincipal userPrincipal = new UserPrincipal();
		userPrincipal.setUsername(username);
		userPrincipal.setPassword("$2a$10$MuuDu7M9Zsw3CsWrODoUFOFcfi1fNoQ2mtdPzdiK7QLYMqErExX46");
		userPrincipal.setActive(true);
		userPrincipal.setAuthorities(new ArrayList<>());*/

        logger.info("[UserDetailsServiceImpl][loadUserByUsername][userPrincipal:" + userPrincipal.toString() +  "]");
        logger.info("[UserDetailsServiceImpl][loadUserByUsername][END]");
        return userPrincipal;
    }

}
