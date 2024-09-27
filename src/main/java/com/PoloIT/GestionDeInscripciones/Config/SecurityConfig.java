package com.PoloIT.GestionDeInscripciones.Config;


import com.PoloIT.GestionDeInscripciones.Enums.Rol;
import com.PoloIT.GestionDeInscripciones.Jwt.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableScheduling
@AllArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity htpp) throws Exception {

        htpp.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/api/v1/auth/**",
                                        "/v3/api-docs/**",
                                        "/api/v1/public/**",
                                        "/swagger-ui/**",
                                        "/swagger-ui.html").permitAll()
                                .requestMatchers("/api/v1/admin/**").hasAnyAuthority(Rol.ADMIN.name())
                                .requestMatchers("/api/v1/student/**").hasAnyAuthority(Rol.STUDENT.name())
                                .requestMatchers("/api/v1/mentor/**").hasAnyAuthority(Rol.MENTOR.name())
                                .requestMatchers("/api/v1/event/").hasAnyAuthority(Rol.STUDENT.name(), Rol.MENTOR.name())
                                .requestMatchers("/api/v1/user/**").hasAnyAuthority(Rol.STUDENT.name(), Rol.MENTOR.name(), Rol.ADMIN.name())
                                .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return htpp.build();
    }
}
