package com.example.demo.service;

import com.example.demo.dto.LoginRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    public UserDetails authenticate(LoginRequestDTO loginRequestDTO) throws Exception {
        logger.info("[AuthenticationService][authenticate][BEGIN]");

        Authentication authentication = null;
        try {
            UsernamePasswordAuthenticationToken authObject = new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
            authentication = authenticationManager.authenticate(authObject);
        }
        catch (BadCredentialsException e) {
            logger.error("[AuthenticationService][authenticate][Error bad credentials: " + e.toString() + "]");
            throw e;
        }

        /*UserDetails userDetails = null;
        try {
            userDetails = userDetailsService.loadUserByUsername(loginRequestDTO.getUsername());
        }
        catch (Exception e) {
            logger.error("[AuthenticationService][authenticate][Error loadUserByUsername: " + e.toString() + "]");
            throw new Exception("[AuthenticationService][authenticate][Error: " + e.toString() + "]");
        }*/

        logger.info("----- User Authenticated Successfully!!! -----");
        logger.info("[AuthenticationService][authenticate][END]");

        return (UserDetails) authentication.getPrincipal();
    }


}
