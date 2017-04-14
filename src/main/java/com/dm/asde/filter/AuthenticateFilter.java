package com.dm.asde.filter;

import com.dm.asde.util.JwtManager;
import com.dm.asde.controller.LogInController;
import com.dm.asde.controller.SignUpController;
import com.dm.asde.model.dto.GeneralErrorNeoDTO;
import com.dm.asde.model.dto.LogInDTO;
import com.dm.asde.model.dto.SignUpDTO;
import com.dm.asde.model.dto.SysError;
import com.dm.asde.util.Messages;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by yinfante on 10/19/16.
 */
public class AuthenticateFilter implements Filter {


    @Autowired
    private JwtManager jwtManager;

    @Autowired
    private Messages messages;


    private Logger logger = Logger.getLogger(AuthenticateFilter.class);


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // forces spring to autowire
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, filterConfig.getServletContext());
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String uri = ((HttpServletRequest) request).getRequestURI();

        if (needFilter(uri)) {

            String authorization = ((HttpServletRequest) request).getHeader("Authorization");

            if (authorization == null || authorization.isEmpty()) {

                HttpServletResponse servletResponse = (HttpServletResponse) response;
                servletResponse.setStatus(HttpStatus.SC_UNAUTHORIZED);

            } else {

                String jws = authorization.replace("Bearer ", "");

                try {

                    if (jwtManager.validToken(jws)) {
                        chain.doFilter(request, response);

                    } else {
                        HttpServletResponse servletResponse = (HttpServletResponse) response;
                        servletResponse.setContentType("application/json");
                        servletResponse.setStatus(HttpStatus.SC_UNAUTHORIZED);
                        SysError error = new SysError();
                        error.setCode("0004");
                        error.setDescription(messages.get("invalid_user_or_password"));
                        GeneralErrorNeoDTO neoError = new GeneralErrorNeoDTO(error);
                        servletResponse.getWriter().write(neoError.toString());
                        servletResponse.getWriter().flush();
                        servletResponse.getWriter().close();
                    }

                } catch (ExpiredJwtException e) {
                    logger.error(e);
                    HttpServletResponse servletResponse = (HttpServletResponse) response;
                    servletResponse.setContentType("application/json");
                    servletResponse.setStatus(HttpStatus.SC_UNAUTHORIZED);
                    SysError sysError = new SysError("0520", messages.get("session_expired"));
                    GeneralErrorNeoDTO neoError = new GeneralErrorNeoDTO(sysError);
                    servletResponse.getWriter().write(neoError.toString());
                    servletResponse.getWriter().flush();
                    servletResponse.getWriter().close();

                } catch (JwtException e) {
                    logger.error(e);
                    HttpServletResponse servletResponse = (HttpServletResponse) response;
                    servletResponse.setContentType("application/json");
                    servletResponse.setStatus(HttpStatus.SC_UNAUTHORIZED);
                    SysError error = new SysError();
                    error.setCode("0004");
                    error.setDescription(messages.get("invalid_user_or_password"));
                    GeneralErrorNeoDTO neoError = new GeneralErrorNeoDTO(error);
                    servletResponse.getWriter().write(neoError.toString());
                    servletResponse.getWriter().flush();
                    servletResponse.getWriter().close();
                }

            }


        } else {

            chain.doFilter(request, response);
        }


    }

    @Override
    public void destroy() {

    }


    private boolean needFilter(String uri) {

        Set<String> paths = new HashSet<>();
        try {
            // path to sign up
            paths.add(linkTo(methodOn(SignUpController.class).signUp(new SignUpDTO())).toUri().getPath());
            // path to log in
            paths.add(linkTo(methodOn(LogInController.class).login(new LogInDTO())).toUri().getPath());
        } catch (Exception e) {
            logger.error(e);
        }

        return !paths.contains(uri);
    }


}
