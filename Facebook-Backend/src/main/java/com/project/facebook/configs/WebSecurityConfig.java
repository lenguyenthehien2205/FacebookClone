package com.project.facebook.configs;

import com.project.facebook.filters.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpMethod.*;

@Configuration
//@EnableMethodSecurity
@EnableWebMvc
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtTokenFilter jwtTokenFilter;
    // cau hinh de loc cac request nham phan quyen
    @Value("${api.base-path}")
    private String apiBasePath;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable) //Tắt bảo vệ CSRF (Cross-Site Request Forgery) do ko can thiet
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class) // filter truoc khi xac thuc
                .authorizeHttpRequests(requests -> {
                    requests
                            .requestMatchers(
                                    String.format("/%s/users/register", apiBasePath),
                                    String.format("/%s/users/login", apiBasePath),
                                    String.format("/%s/friends/{user_id}", apiBasePath),
                                    String.format("/%s/profiles/avatar_image/{image_name}",apiBasePath),
                                    String.format("/%s/profiles/cover_photo_image/{image_name}",apiBasePath),
                                    String.format("/%s/medias/image_post/{image_name}", apiBasePath),
                                    String.format("/%s/medias/video_post/{video_name}", apiBasePath)

                            )
                            .permitAll()
                            .requestMatchers(GET, String.format("/%s/users/{phone}", apiBasePath)).hasRole("ADMIN")
                            .requestMatchers(GET, String.format("/%s/users", apiBasePath)).hasRole("ADMIN")
                            .requestMatchers(GET, String.format("/%s/users/images/{imageName}", apiBasePath)).permitAll()
                            .requestMatchers(POST, String.format("/%s/users/upload_avatar/{user_id}", apiBasePath)).hasRole("USER")

                            .requestMatchers(POST, String.format("/%s/friends/{first_profile_id}/{second_profile_id}", apiBasePath)).hasRole("USER")
                            .requestMatchers(POST, String.format("/%s/friends/contacts/{user_id}", apiBasePath)).hasRole("USER")

                            .requestMatchers(POST, String.format("/%s/posts", apiBasePath)).hasRole("USER")
                            .requestMatchers(PUT, String.format("/%s/posts/{post_id}", apiBasePath)).hasRole("USER")
                            .requestMatchers(GET, String.format("/%s/posts/friend-posts/{user_id}", apiBasePath)).hasRole("USER")
                            .requestMatchers(POST, String.format("/%s/posts/random-authors-latest", apiBasePath)).permitAll()

                            .requestMatchers(GET, String.format("/%s/medias/post/{post_id}", apiBasePath)).hasRole("USER")
                            .requestMatchers(POST, String.format("/%s/medias/upload_medias_post/{post_id}/{note}", apiBasePath)).hasRole("USER")

                            .requestMatchers(GET, String.format("/%s/interactions/media/{media_id}", apiBasePath)).permitAll()
                            .requestMatchers(GET, String.format("/%s/interactions/post/{post_id}", apiBasePath)).permitAll()
                            .requestMatchers(GET, String.format("/%s/interactions/post/detail/{post_id}", apiBasePath)).permitAll()
                            .requestMatchers(GET, String.format("/%s/interactions/post/{post_id}/{interaction_type}", apiBasePath)).permitAll()
                            .requestMatchers(GET, String.format("/%s/interactions/media/detail/{media_id}", apiBasePath)).permitAll()

                            .requestMatchers(POST, String.format("/%s/profiles/upload_avatar/{profile_id}", apiBasePath)).hasRole("USER")
                            .requestMatchers(POST, String.format("/%s/profiles/upload_cover_photo/{profile_id}", apiBasePath)).hasRole("USER")
//                            .requestMatchers(GET, String.format("/%s/profiles/avatar_image/{imageName}", apiBasePath)).permitAll()
//                            .requestMatchers(GET, String.format("/%s/profiles/cover_photo_image/{imageName}", apiBasePath)).permitAll()
//                            .requestMatchers(GET,
//                                    String.format("/%s/users", apiBasePath)).hasRole("ADMIN")
                            .anyRequest().authenticated();
                });
        http.cors(new Customizer<CorsConfigurer<HttpSecurity>>() {
            @Override
            public void customize(CorsConfigurer<HttpSecurity> httpSecurityCorsConfigurer) {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(List.of("*"));
                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
                configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
                configuration.setExposedHeaders(List.of("x-auth-token"));
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                httpSecurityCorsConfigurer.configurationSource(source);
            }
        });
        return http.build();
    }
}
