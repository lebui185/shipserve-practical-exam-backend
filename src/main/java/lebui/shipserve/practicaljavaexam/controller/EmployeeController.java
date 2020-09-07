package lebui.shipserve.practicaljavaexam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lebui.shipserve.practicaljavaexam.dto.EmployeeDTO;
import lebui.shipserve.practicaljavaexam.dto.EmployeeSaveDTO;
import lebui.shipserve.practicaljavaexam.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    
    @GetMapping(value = "/{employeeId}", produces = "application/json")
    public EmployeeDTO findById(@PathVariable Long employeeId) {
        return this.employeeService.findEmployeeById(employeeId);
    }
    
    @GetMapping(produces = "application/json")
    public List<EmployeeDTO> findAllEmployees() {
        return this.employeeService.findAllEmployees();
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public EmployeeDTO createEmployee(@RequestBody EmployeeSaveDTO employeeSaveDTO) {
        return this.employeeService.createEmployee(employeeSaveDTO);
    }

    @PutMapping(value = "/{employeeId}", produces = "application/json", consumes = "application/json")
    public EmployeeDTO updateEmployee(@PathVariable Long employeeId, @RequestBody EmployeeSaveDTO employeeSaveDTO) {
        return this.employeeService.updateEmployee(employeeId, employeeSaveDTO);
    }
    
    @DeleteMapping(value = "/{employeeId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEmployee(@PathVariable Long employeeId) {
        this.employeeService.deleteEmployee(employeeId);
    }

}
