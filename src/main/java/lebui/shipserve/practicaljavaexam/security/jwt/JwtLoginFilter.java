package lebui.shipserve.practicaljavaexam.security.jwt;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lebui.shipserve.practicaljavaexam.Constant;
import lebui.shipserve.practicaljavaexam.entity.Employee;
import lebui.shipserve.practicaljavaexam.mapper.EmployeeMapper;
import lebui.shipserve.practicaljavaexam.repository.EmployeeRepository;
import lebui.shipserve.practicaljavaexam.security.SecurityUser;

public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private EmployeeMapper employeeMapper;
    
    public JwtLoginFilter(AuthenticationManager authenticationManager) {
        this.setAuthenticationManager(authenticationManager);
        setFilterProcessesUrl(Constant.SECURITY.JWT_LOGIN_URL);
    }
    
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        return getAuthenticationManager().authenticate(authenticationToken);
    }
    
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication authentication) throws IOException, ServletException {
        SecurityUser securityUser = ((SecurityUser) authentication.getPrincipal());

        List<String> roles = securityUser.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

        SecretKey secretKey = Keys.hmacShaKeyFor(Constant.SECURITY.JWT_SECRET.getBytes(StandardCharsets.UTF_8));
        
        String token = Jwts.builder()
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .setHeaderParam(Constant.JWT_CLAIM.TYPE, Constant.JWT_CLAIM.TOKEN_TYPE)
            .setSubject(securityUser.getUsername())
            .claim(Constant.JWT_CLAIM.ROLES, roles)
            .compact();

        LoginSuccessResponse loginSuccessResponse = buildLoginSuccessResponse(securityUser, token);
        
        response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), loginSuccessResponse);
    }

    private LoginSuccessResponse buildLoginSuccessResponse(SecurityUser securityUser, String token) {
        LoginSuccessResponse loginSuccessResponse = new LoginSuccessResponse();
        loginSuccessResponse.setToken(token);
        
        Employee employee = this.employeeRepository.findById(securityUser.getId()).get();
        LoggedInUser loggedInUser = this.employeeMapper.mapEmployeeToLoggedInUser(employee);
        loginSuccessResponse.setUser(loggedInUser);
        
        return loginSuccessResponse;
    }
    
}
