package wanted.goldroom.authentication.infrastructure.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import wanted.goldroom.authentication.infrastructure.jwt.JwtProvider;
import wanted.goldroom.authentication.interfaces.filter.AuthExceptionHandlerFilter;
import wanted.goldroom.authentication.interfaces.filter.JwtFilter;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {
    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;

    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilter() {
        FilterRegistrationBean<JwtFilter> jwtFilter = new FilterRegistrationBean<>();
        jwtFilter.setFilter(new JwtFilter(jwtProvider));
        jwtFilter.addUrlPatterns("/api/*");
        jwtFilter.setOrder(2);
        return jwtFilter;
    }

    @Bean
    public FilterRegistrationBean<AuthExceptionHandlerFilter> authExceptionHandlerFilter() {
        FilterRegistrationBean<AuthExceptionHandlerFilter> authExceptionHandlerFilter = new FilterRegistrationBean<>();
        authExceptionHandlerFilter.setFilter(new AuthExceptionHandlerFilter(objectMapper));
        authExceptionHandlerFilter.addUrlPatterns("/api/*");
        authExceptionHandlerFilter.setOrder(1);
        return authExceptionHandlerFilter;
    }
}
