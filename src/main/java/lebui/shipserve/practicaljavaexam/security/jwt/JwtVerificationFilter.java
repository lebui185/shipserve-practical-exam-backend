package lebui.shipserve.practicaljavaexam.security.jwt;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lebui.shipserve.practicaljavaexam.Constant;

public class JwtVerificationFilter extends OncePerRequestFilter {
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Authentication authentication = getAuthentication(request);
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        
        filterChain.doFilter(request, response);
    }
    
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String tokenWithPrefix = request.getHeader(Constant.SECURITY.TOKEN_HEADER);
        if (StringUtils.isEmpty(tokenWithPrefix) || !tokenWithPrefix.startsWith(Constant.SECURITY.TOKEN_PREFIX)) {
            return null;
        }
        
        byte[] signingKey = Constant.SECURITY.JWT_SECRET.getBytes(StandardCharsets.UTF_8);
        JwtParser jwtParser = Jwts.parserBuilder()
            .setSigningKey(signingKey)
            .build();
        
        String token = tokenWithPrefix.replace(Constant.SECURITY.TOKEN_PREFIX, "");
        Jws<Claims> parsedToken = jwtParser.parseClaimsJws(token);

        String username = parsedToken.getBody().getSubject();
        Object rolesClaim = parsedToken.getBody().get(Constant.JWT_CLAIM.ROLES);

        List<SimpleGrantedAuthority> authorities = ((List<?>) rolesClaim).stream()
            .map(authority -> new SimpleGrantedAuthority((String) authority))
            .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }

}
