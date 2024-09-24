package com.project.facebook.filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.project.facebook.components.JwtTokenUtils;
import com.project.facebook.models.User;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    @Value("${api.base-path}")
    private String apiBasePath;
    private final JwtTokenUtils jwtTokenUtil;
    private final UserDetailsService userDetailsService;
    // nhu the nao kiem tra, cho di qua
    // login va register thi khong kiem tra token
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try{
            //  filterChain.doFilter(request, response); // cho di qua
            if (isBypassToken(request)){
                filterChain.doFilter(request, response);
                return;
            }
            // neu co kiem tra
            final String authHeader = request.getHeader("Authorization");
            if(authHeader == null || !authHeader.startsWith("Bearer ")){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                return;
            }
            final String token = authHeader.substring(7);
            final String phoneNumber = jwtTokenUtil.extractPhoneNumber(token);
            // neu co phone number trong token va chua duoc xac thuc
            if(phoneNumber != null && SecurityContextHolder.getContext().getAuthentication() == null){
                User user = (User) userDetailsService.loadUserByUsername(phoneNumber); // ham da duoc cau hinh o security config
                if (jwtTokenUtil.validateToken(token, user)){ // kiem tra xem du lieu trong token va trong db co khop nhau khong(da het han chua)
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    user,null, user.getAuthorities()
                            );
                    // gan cac chi tiet cua request vao authenticationToken
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // nguoi dung duoc xac thuc(cho phep truy cap tai nguyen) va cac thong tin se duoc su dung trong cac lan xu ly tiep theo cua request
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            filterChain.doFilter(request, response);
        }catch (Exception e){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
    }

    private boolean isBypassToken(@NonNull HttpServletRequest request){
        // cac request duoc cho qua ma khong can kiem tra token
        final List<Pair<String, String>> bypassTokens = Arrays.asList(
                Pair.of(String.format("/%s/users/register", apiBasePath),"POST"),
                Pair.of(String.format("/%s/users/login", apiBasePath),"POST"),
                Pair.of(String.format("/%s/users/images/", apiBasePath),"GET")
        );
        for (Pair<String, String> bypassToken: bypassTokens){
            if(request.getServletPath().contains(bypassToken.getFirst()) && request.getMethod().equals(bypassToken.getSecond())){
                return true;
            }
        }
        return false;
    }
}
