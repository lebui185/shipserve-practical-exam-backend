package lebui.shipserve.practicaljavaexam.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lebui.shipserve.practicaljavaexam.entity.Employee;
import lebui.shipserve.practicaljavaexam.mapper.EmployeeMapper;
import lebui.shipserve.practicaljavaexam.repository.EmployeeRepository;

@Service
public class DatabaseDetailsUserService implements UserDetailsService {
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private EmployeeMapper employeeMapper;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = this.employeeRepository.findByUsername(username);
        if (employee == null) {
            throw new UsernameNotFoundException("Username is not found: " + username);
        }
        
        SecurityUser securityUser = this.employeeMapper.mapEmployeeToSecurityUser(employee);
        return securityUser;
    }

}
