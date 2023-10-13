package br.com.atilara.todolist.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.atilara.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        var servletPath = request.getServletPath();
        System.out.println(servletPath);
        System.out.println(servletPath.startsWith("/users"));
        if (servletPath.startsWith("/users")) {
            filterChain.doFilter(request, response);
            return;
        }

        var authorization = request.getHeader("Authorization");
        authorization = authorization.substring("Basic ".length()).trim();
        var decoded = new String(java.util.Base64.getDecoder().decode(authorization));
        var username = decoded.split(":")[0];
        var password = decoded.split(":")[1];

        var user = this.userRepository.findByUsername(username);
        if (user == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword().toCharArray());
            if (passwordVerify.verified) {
                request.setAttribute("userId", user.getId());
                filterChain.doFilter(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
    }
}
