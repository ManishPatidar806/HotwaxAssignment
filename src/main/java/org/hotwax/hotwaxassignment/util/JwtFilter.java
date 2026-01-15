package org.hotwax.hotwaxassignment.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtSecurity jwtSecurity;
    private final UserDetailsService userDetailsService;

    public JwtFilter(JwtSecurity jwtSecurity, UserDetailsService userDetailsService) {
        this.jwtSecurity = jwtSecurity;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");
        String token = null;
        String mobileNo = null;

        try {
            if (authorization != null && authorization.startsWith("Bearer ")) {
                token = authorization.substring(7).trim();
                mobileNo = jwtSecurity.extractMobile(token);
            }

            if (mobileNo != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(mobileNo);

                if (jwtSecurity.validateToken(token, userDetails.getUsername())) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());

                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

        } catch (Exception e) {

            System.out.println("JWT Filter Error: " + e.getMessage());
        }


        filterChain.doFilter(request, response);
    }
}
