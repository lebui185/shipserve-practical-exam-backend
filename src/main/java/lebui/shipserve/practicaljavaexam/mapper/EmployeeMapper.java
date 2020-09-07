package lebui.shipserve.practicaljavaexam.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import lebui.shipserve.practicaljavaexam.dto.EmployeeDTO;
import lebui.shipserve.practicaljavaexam.entity.Employee;
import lebui.shipserve.practicaljavaexam.entity.Role;
import lebui.shipserve.practicaljavaexam.security.SecurityUser;
import lebui.shipserve.practicaljavaexam.security.jwt.LoggedInUser;

@Component
public class EmployeeMapper {
    
    public SecurityUser mapEmployeeToSecurityUser(Employee employee) {
        SecurityUser securityUser = new SecurityUser();
        securityUser.setId(employee.getId());
        securityUser.setFirstName(employee.getFirstName());
        securityUser.setSurname(employee.getSurname());
        securityUser.setEmail(employee.getEmail());
        securityUser.setUsername(employee.getUsername());
        securityUser.setPassword(employee.getPassword());
        securityUser.setActive(employee.getActive());
        
        List<SimpleGrantedAuthority> authorities = employee.getRoles().stream()
            .map(this::mapRoleToSimpleGrantedAuthority)
            .collect(Collectors.toList());
        
        securityUser.setAuthorities(authorities);
        
        return securityUser;
    }
    
    public EmployeeDTO mapEmployeeToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        
        employeeDTO.setId(employee.getId());
        employeeDTO.setFirstName(employee.getFirstName());
        employeeDTO.setSurname(employee.getSurname());
        employeeDTO.setEmail(employee.getEmail());
        employeeDTO.setUsername(employee.getUsername());
        employeeDTO.setActive(employee.getActive());
        
        if (employee.getCompany() != null) {
            employeeDTO.setCompanyId(employee.getCompany().getId());
        }
        
        Set<String> roles = employee.getRoles().stream()
            .map(role -> role.getName())
            .collect(Collectors.toSet());
        employeeDTO.setRoles(roles);
        
        return employeeDTO;
    }
    
    private SimpleGrantedAuthority mapRoleToSimpleGrantedAuthority(Role role) {
        return new SimpleGrantedAuthority("ROLE_" + role.getName());
    }

    public LoggedInUser mapEmployeeToLoggedInUser(Employee employee) {
        LoggedInUser loggedInUser = new LoggedInUser();
        
        loggedInUser.setId(employee.getId());
        loggedInUser.setFirstName(employee.getFirstName());
        loggedInUser.setSurname(employee.getSurname());
        loggedInUser.setEmail(employee.getEmail());
        loggedInUser.setUsername(employee.getUsername());
        loggedInUser.setActive(employee.getActive());
        
        if (employee.getCompany() != null) {
            loggedInUser.setCompanyId(employee.getCompany().getId());
        }
        
        Set<String> roles = employee.getRoles().stream()
            .map(role -> role.getName())
            .collect(Collectors.toSet());
        loggedInUser.setRoles(roles);
        
        return loggedInUser;
    }

}
