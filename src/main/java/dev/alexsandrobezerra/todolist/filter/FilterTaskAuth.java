package dev.alexsandrobezerra.todolist.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import dev.alexsandrobezerra.todolist.user.UserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    private final UserRepository userRepository;

    public FilterTaskAuth(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final var path = request.getServletPath();

        if (path.startsWith("/tasks")) {
            final var authorization = request.getHeader("Authorization");
            if (authorization == null || !authorization.startsWith("Basic ")) {
                response.sendError(HttpStatus.UNAUTHORIZED.value());
                return;
            }
            final var basicAuthEncoded = authorization.substring("Basic".length()).trim();
            final var basicAuthDecoded = new String(Base64.getDecoder().decode(basicAuthEncoded));
            final var usernameAndPassword = basicAuthDecoded.split(":");
            final var username = usernameAndPassword[0];
            final var password = usernameAndPassword[1];

            final var user = this.userRepository.findByUsername(username);
            if (user == null) {
                response.sendError(HttpStatus.UNAUTHORIZED.value());
                return;
            }

            final var result = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword().toCharArray());
            if (!result.verified) {
                response.sendError(HttpStatus.UNAUTHORIZED.value());
                return;
            }

            request.setAttribute("userId", user.getId());
            filterChain.doFilter(request, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }

}
