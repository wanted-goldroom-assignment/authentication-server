package wanted.goldroom.authentication.interfaces.filter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import wanted.goldroom.authentication.domain.exception.ErrorCode;
import wanted.goldroom.authentication.domain.exception.UnAuthorizedException;
import wanted.goldroom.authentication.infrastructure.jwt.JwtProvider;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private static final String BEARER = "bearer";

    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    private final List<String> excludeUrlPatterns = List.of("/api/users/auth/**");

    private final JwtProvider jwtProvider;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return excludeUrlPatterns.stream()
            .anyMatch(pattern -> pathMatcher.match(pattern, request.getRequestURI()));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        if (CorsUtils.isPreFlightRequest(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = extractJwt(request)
            .orElseThrow(() -> new UnAuthorizedException(ErrorCode.INVALID_AUTH_HEADER));
        jwtProvider.validateToken(token);

        filterChain.doFilter(request, response);
    }

    private Optional<String> extractJwt(HttpServletRequest request) {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasText(header) || !header.toLowerCase().startsWith(BEARER)) {
            return Optional.empty();
        }
        return Optional.of(header.split(" ")[1]);
    }
}
