package com.softwarefoundation.fooddeliverycontroleacesso.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    public JwtRequestFilter(JwtTokenUtil jwtTokenUtil, JwtUserDetailsService jwtUserDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");

        log.info(requestTokenHeader);

        String login = null;
        String jwtToken = null;

// JWT Token está no form "Bearer token". Remova a palavra Bearer e pegue somente o Token
        if (requestTokenHeader != null) {
            jwtToken = requestTokenHeader; //.substring(7);
            try {
                login = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                log.info("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                log.info("JWT Token has expired");
            }
        } else {
            log.warn("JWT Token does not begin with Bearer String");
        }

        // Tendo o token, valide o.
        if (login != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            log.info("Usário login: {}", login);

            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(login);

            log.info(userDetails.toString());

            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        chain.doFilter(request, response);
    }

}
