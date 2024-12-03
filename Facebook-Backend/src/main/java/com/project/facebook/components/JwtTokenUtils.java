package com.project.facebook.components;

import java.security.InvalidParameterException;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import com.project.facebook.models.Page;
import com.project.facebook.models.PageBase;
import com.project.facebook.models.Profile;
import com.project.facebook.services.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.project.facebook.models.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenUtils {
    private final IPageBaseService pageBaseService;
    private final IPageService pageService;
    private final IProfileService profileService;
    @Value("${jwt.expiration}")
    private int expiration;
    @Value("${jwt.secretKey}")
    private String secretKey;

    // ham tao token dau vao la User
    public String genarateToken(User user) throws Exception{
        // Truyen cac gia tri vao cac claim(khong chi la phoneNumber)
        Map<String, Object> claims = new HashMap<>();
//        this.generateSecretKey();
        String currentPageType = user.getCurrentPageType();
        Long currentPageId = user.getCurrentPageId();
        String avatar = "";
        if(currentPageType.equals(User.PROFILE)){
            Profile profile = profileService.getProfileById(currentPageId);
            PageBase pageBase = pageBaseService.getPageBaseById(profile.getPageBase().getId());
            avatar = pageBase.getAvatar();
            claims.put("pageType", User.PROFILE);
            claims.put("firstName", profile.getFirstName());
            claims.put("lastName", profile.getLastName());
            claims.put("displayFormat", profile.getDisplayFormat());
            claims.put("currentPageType", user.getCurrentPageType());
            claims.put("currentPageId", user.getCurrentPageId());
        }else if (currentPageType.equals(User.PAGE)){
            Page page = pageService.getPageById(currentPageId);
            PageBase pageBase = pageBaseService.getPageBaseById(page.getPageBase().getId());
            avatar = pageBase.getAvatar();
            claims.put("pageType", User.PAGE);
            claims.put("fullName", page.getPageName());
            claims.put("currentPageType", user.getCurrentPageType());
            claims.put("currentPageId", user.getCurrentPageId());
        }
        claims.put("phoneNumber", user.getUsername());
        claims.put("userId", user.getUserId());
        claims.put("avatar", avatar);

//        claims.put("avatar", pageBaseService.(user.get);
        try{
            String token = Jwts.builder()
                    .setClaims(claims) // truyen claims vao token
                    .setSubject(user.getUsername())
                    .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256) // sau nay truyen secret key de lay cac claim
                    .compact();
            return token;
        } catch (Exception e){
            // sau nay co the dung logger thay vi sout
            throw new InvalidParameterException("Cannot create jwt token, error: "+e.getMessage());
        }
    }

    // Chuyen doi secretKey (String) => Key
    private Key getSignInKey(){// co the dung Key bi mat nay de ky va lay ra Claim => Tranh bi tan cong, chi co server giai ma dc secret key
        // giai ma secret key (o dinh dang BASE64) va tao ra Key
        byte[] bytes = Decoders.BASE64URL.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }

//    private String generateSecretKey(){
//        SecureRandom random = new SecureRandom();
//        byte[] keyBytes = new byte[32];
//        random.nextBytes(keyBytes);
//        String secretKey = Encoders.BASE64.encode(keyBytes);
//        return secretKey;
//    }

    // Dung Key va token de lay ra Claim
    private Claims extractAllClaims(String token){
        // phan tich token va xac minh key, neu dung getBody de lay tat ca claims, neu sai nem exception
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = this.extractAllClaims(token);
        return claimsResolver.apply(claims); // no se goi ham do nguoi dung truyen vao
    }

    private boolean isTokenExpired(String token){
        Date expirationDate = this.extractClaim(token, Claims::getExpiration); // method reference
        return expirationDate.before(new Date());
    }

    public String extractPhoneNumber(String token){
        return extractClaim(token, Claims::getSubject);
    }
    public boolean validateToken(String token, UserDetails userDetails){
        String phoneNumber = extractPhoneNumber(token);
        return (phoneNumber.equals(((User) userDetails).getUsername()) && !isTokenExpired(token));
    }
}
