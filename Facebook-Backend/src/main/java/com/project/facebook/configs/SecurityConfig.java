package com.project.facebook.configs;

import com.project.facebook.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserRepository userRepository;
    // loadUserByUsername(phoneNumber): ham nay duoc chay va truyen phoneNumber vao (no tra ve user co phoneNumber)
//    @Bean
//    public UserDetailsService userDetailsService(){
//        return phoneNumber -> userRepository
//                .findByPhoneNumber(phoneNumber)
//                .orElseThrow(() -> new UsernameNotFoundException(("Can't find user with phone number: "+phoneNumber)));
//    }
    @Bean
    public UserDetailsService userDetailsService() {
        return phoneNumber -> userRepository
                .findByUsername(phoneNumber)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Cannot find user with phone number = "+phoneNumber));
    }

    // khi chay tu ma hoa password
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // khoi tao authenticationProvider
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        // dung UserDetailsService de tim nguoi dung thong qua sdt
        authenticationProvider.setUserDetailsService(userDetailsService());
        // dung PasswordEncoder de kiem tra mat khau
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    // AuthenticationManager se su dung cac AuthenticationProvider da duoc cung cap khi dung ham authenticate()
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config
    ) throws Exception{
        return config.getAuthenticationManager();
    }
}
// 1. User ke thua tu UserDeail va config no
// 2. Tao SecurityConfig
// 3. Cau hinh phan quyen bang WebSecurityConfig
// 4. Cau hinh phuong thuc login -> jwt token
// 5. Cau hinh JwtTokenUtil(de tra ve token)
// 6. authenticate() thanh cong o user service truoc khi genarate token
// 7. Tao JwtTokenFilter de kiem tra xem request gui den co token khong