package com.github.coder229.diary.security
import io.jsonwebtoken.ExpiredJwtException
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.web.filter.GenericFilterBean

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by scott on 9/18/2015.
 */
@Service
class StatelessAuthFilter extends GenericFilterBean {

    @Autowired
    TokenAuthService authService

    @Autowired
    GlobalControllerExceptionHandler exceptionHandler

    @Override
    void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request
        HttpServletResponse httpResponse = (HttpServletResponse) response
        try {
            SecurityContextHolder.getContext().setAuthentication(authService.getAuthentication(httpRequest))
            filterChain.doFilter(request, response)
            SecurityContextHolder.getContext().setAuthentication(null)
        } catch (ExpiredJwtException e) {
            LogFactory.getLog(getClass()).warn("Session expired", e)
            httpResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "Session expired")
        }
    }
}