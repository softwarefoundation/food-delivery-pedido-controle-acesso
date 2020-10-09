package com.softwarefoundation.fooddeliverycontroleacesso.controller;

import com.softwarefoundation.fooddeliverycontroleacesso.jwt.dto.JwtRequest;
import com.softwarefoundation.fooddeliverycontroleacesso.jwt.dto.JwtResponse;
import com.softwarefoundation.fooddeliverycontroleacesso.jwt.JwtTokenUtil;
import com.softwarefoundation.fooddeliverycontroleacesso.jwt.JwtUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RestController
public class JwtAuthenticationController {

    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public JwtAuthenticationController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, JwtUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        log.info("Criando token de autenticação");

        authenticate(authenticationRequest.getLogin(), authenticationRequest.getSenha());
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getLogin());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @GetMapping("/teste")
    public String teste() {
        return "ok";
    }

    private void authenticate(String login, String senha) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, senha));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
