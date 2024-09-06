package wanted.goldroom.authentication.interfaces.filter;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import wanted.goldroom.authentication.domain.exception.ErrorCode;
import wanted.goldroom.authentication.domain.exception.ErrorResponse;
import wanted.goldroom.authentication.domain.exception.UnAuthorizedException;

@RequiredArgsConstructor
public class AuthExceptionHandlerFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (UnAuthorizedException e) {
            setErrorResponse(response, e.getErrorCode());
        }
    }

    private void setErrorResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), errorCode.getMessage());
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
