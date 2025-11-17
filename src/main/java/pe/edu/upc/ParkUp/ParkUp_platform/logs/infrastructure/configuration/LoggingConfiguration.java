package pe.edu.upc.ParkUp.ParkUp_platform.logs.infrastructure.configuration;

import pe.edu.upc.ParkUp.ParkUp_platform.logs.infrastructure.middleware.AdminLoggingInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration for admin logging interceptor
 * Automatically logs admin actions for audit purposes
 */
@Configuration
public class LoggingConfiguration implements WebMvcConfigurer {
    
    private final AdminLoggingInterceptor adminLoggingInterceptor;
    
    public LoggingConfiguration(AdminLoggingInterceptor adminLoggingInterceptor) {
        this.adminLoggingInterceptor = adminLoggingInterceptor;
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminLoggingInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns(
                    "/api/v1/authentication/**",
                    "/api/logs/**",
                    "/v3/api-docs/**",
                    "/swagger-ui/**",
                    "/swagger-resources/**",
                    "/webjars/**"
                );
    }
}