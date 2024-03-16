package com.example.demo.controller;


import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.dto.LoginResponseDTO;
import com.example.demo.service.AuthenticationService;
import com.example.demo.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<LoginResponseDTO> authenticate(@RequestBody LoginRequestDTO loginRequestDTO) throws Exception {
        logger.info("[LoginController][authenticate][BEGIN][loginRequestDTO: " + loginRequestDTO.toString() + "]");

        UserDetails userPrincipal = authenticationService.authenticate(loginRequestDTO);
        logger.info("[LoginController][authenticate][userDetails: " + userPrincipal.toString() + "]");

        /*
        UserPrincipal userPrincipal = new UserPrincipal();
        userPrincipal.setUsername("user");
        userPrincipal.setActive(true);
        */

        String tokenJWT = JwtUtil.generateToken(userPrincipal);
        logger.info("[LoginController][authenticate][tokenJWT: " + tokenJWT + "]");

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(loginRequestDTO.getUsername(), tokenJWT);
        logger.info("[LoginController][authenticate][loginResponseDTO: " + loginResponseDTO.toString() + "]");

        logger.info("[LoginController][authenticate][END]");
        return ResponseEntity.ok().body(loginResponseDTO);
    }

}
