package lebui.shipserve.practicaljavaexam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import lebui.shipserve.practicaljavaexam.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

    Employee findByUsername(String username);

    List<Employee> findByCompanyId(Long companyId);

}
