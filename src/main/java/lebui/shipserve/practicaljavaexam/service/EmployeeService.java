package lebui.shipserve.practicaljavaexam.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lebui.shipserve.practicaljavaexam.Constant;
import lebui.shipserve.practicaljavaexam.dto.EmployeeDTO;
import lebui.shipserve.practicaljavaexam.dto.EmployeeSaveDTO;
import lebui.shipserve.practicaljavaexam.entity.Employee;
import lebui.shipserve.practicaljavaexam.entity.Role;
import lebui.shipserve.practicaljavaexam.exception.NotFoundException;
import lebui.shipserve.practicaljavaexam.mapper.EmployeeMapper;
import lebui.shipserve.practicaljavaexam.repository.CompanyRepository;
import lebui.shipserve.practicaljavaexam.repository.EmployeeRepository;
import lebui.shipserve.practicaljavaexam.repository.RoleRepository;
import lebui.shipserve.practicaljavaexam.specification.EmployeeSpecification;

@Service
@Transactional
public class EmployeeService {
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private EmployeeMapper employeeMapper;
    
    @Autowired
    private CompanyRepository companyRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private AuthenticationService authenticationService;
    
    public EmployeeDTO findEmployeeById(Long employeeId) {
        Employee employee = this.employeeRepository
            .findById(employeeId)
            .orElseThrow(() -> new NotFoundException("Employee ID is not found: " + employeeId));
        if (this.authenticationService.hasAnyRoles(Constant.USER_ROLE.COMPANY_ADMIN, Constant.USER_ROLE.USER)
            && !this.authenticationService.isLoggedInUserHasPermissionOnCompany(employee.getCompany())
        ) {
            throw new AccessDeniedException("");
        }
        
        return this.employeeMapper.mapEmployeeToEmployeeDTO(employee);
    }
    
    public List<EmployeeDTO> findAllEmployees() {
        Specification<Employee> employeeSpec = buildEmployeeSpecificationFromRole();
        
        return this.employeeRepository.findAll(employeeSpec).stream()
            .map(employeeMapper::mapEmployeeToEmployeeDTO)
            .collect(Collectors.toList());
    }

    private Specification<Employee> buildEmployeeSpecificationFromRole() {
        Specification<Employee> employeeSpec = null;
        
        if (this.authenticationService.hasAnyRoles(Constant.USER_ROLE.SUPER_ADMIN)) {
            employeeSpec = EmployeeSpecification.all();
        } else if (this.authenticationService.hasAnyRoles(Constant.USER_ROLE.COMPANY_ADMIN, Constant.USER_ROLE.USER)) {
            Long loggedInUserCompanyId = this.authenticationService.getLoggedInUser().getCompanyId();
            employeeSpec = EmployeeSpecification.companyId(loggedInUserCompanyId);
        }
        
        return employeeSpec;
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'COMPANY_ADMIN')")
    public EmployeeDTO createEmployee(EmployeeSaveDTO employeeSaveDTO) {
        Employee newEmployee = new Employee();
        newEmployee.setUsername(employeeSaveDTO.getUsername());
        
        saveEmployeeSaveDTOToEmployee(employeeSaveDTO, newEmployee);
        if (this.authenticationService.hasAnyRoles(Constant.USER_ROLE.COMPANY_ADMIN)
            && !this.authenticationService.isLoggedInUserHasPermissionOnCompany(newEmployee.getCompany())
        ) {
            throw new AccessDeniedException("");
        }
        
        newEmployee = this.employeeRepository.save(newEmployee);
        
        return this.employeeMapper.mapEmployeeToEmployeeDTO(newEmployee);
    }
    
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'COMPANY_ADMIN')")
    public EmployeeDTO updateEmployee(Long employeeId, EmployeeSaveDTO employeeSaveDTO) {
        Employee updatingEmployee = this.employeeRepository
            .findById(employeeId)
            .orElseThrow(() -> new NotFoundException("Employee ID is not found: " + employeeId));
        
        if (this.authenticationService.hasAnyRoles(Constant.USER_ROLE.COMPANY_ADMIN)
            && !this.authenticationService.isLoggedInUserHasPermissionOnCompany(updatingEmployee.getCompany())
        ) {
            throw new AccessDeniedException("");
        }
        
        saveEmployeeSaveDTOToEmployee(employeeSaveDTO, updatingEmployee);
        
        return this.employeeMapper.mapEmployeeToEmployeeDTO(updatingEmployee);
    }

    private void saveEmployeeSaveDTOToEmployee(EmployeeSaveDTO employeeSaveDTO, Employee employee) {
        employee.setFirstName(employeeSaveDTO.getFirstName());
        employee.setSurname(employeeSaveDTO.getSurname());
        employee.setEmail(employeeSaveDTO.getEmail());
        employee.setActive(employeeSaveDTO.getActive());
        
        if (StringUtils.isNotBlank(employeeSaveDTO.getPassword())) {
            String encodedPassword = this.passwordEncoder.encode(employeeSaveDTO.getPassword());
            employee.setPassword(encodedPassword);
        }
        
        employee.setCompany(this.companyRepository.getOne(employeeSaveDTO.getCompanyId()));
        saveEmployeeRoles(employeeSaveDTO.getRoles(), employee.getRoles());
    }

    private void saveEmployeeRoles(Set<String> updatingRoleString, Set<Role> currentRoles) {
        Set<Role> updatingRoles = updatingRoleString.stream()
            .map(roleString -> this.roleRepository.findByName(roleString))
            .collect(Collectors.toSet());
        
        Collection<Role> removedRoles = CollectionUtils.subtract(currentRoles, updatingRoles);
        currentRoles.removeAll(removedRoles);
        
        Collection<Role> newRoles = CollectionUtils.subtract(updatingRoles, currentRoles);
        currentRoles.addAll(newRoles);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'COMPANY_ADMIN')")
    public void deleteEmployee(Long employeeId) {
        // use update to prevent cascading
        Employee deletingEmployee = this.employeeRepository
            .findById(employeeId)
            .orElseThrow(() -> new NotFoundException("Employee ID is not found: " + employeeId));
        
        if (this.authenticationService.hasAnyRoles(Constant.USER_ROLE.COMPANY_ADMIN)
            && !this.authenticationService.isLoggedInUserHasPermissionOnCompany(deletingEmployee.getCompany())
        ) {
            throw new AccessDeniedException("");
        }
        
        deletingEmployee.setDeleted(true);
    }
    
}
