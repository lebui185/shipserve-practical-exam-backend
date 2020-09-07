package lebui.shipserve.practicaljavaexam.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lebui.shipserve.practicaljavaexam.entity.Company;
import lebui.shipserve.practicaljavaexam.entity.Employee;
import lebui.shipserve.practicaljavaexam.mapper.EmployeeMapper;
import lebui.shipserve.practicaljavaexam.repository.EmployeeRepository;
import lebui.shipserve.practicaljavaexam.security.jwt.LoggedInUser;

@Service
public class AuthenticationService {
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private EmployeeMapper employeeMapper;

    public LoggedInUser getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        
        String username = (String) authentication.getPrincipal();
        Employee employee = this.employeeRepository.findByUsername(username);
        LoggedInUser loggedInUser = employeeMapper.mapEmployeeToLoggedInUser(employee);
        
        return loggedInUser;
    }

    public boolean hasAnyRoles(String ...inputRoles) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }
        
        for (String inputRole : inputRoles) {
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if (Objects.equals(authority.getAuthority(), "ROLE_" + inputRole)) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    public boolean isLoggedInUserHasPermissionOnCompany(Company company) {
        if (company == null) {
            return false;
        }
        
        return Objects.equals(company.getId(), getLoggedInUser().getCompanyId());
    }
    
}
