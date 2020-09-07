package lebui.shipserve.practicaljavaexam.specification;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import lebui.shipserve.practicaljavaexam.entity.Employee;

public class EmployeeSpecification {
    
    public static Specification<Employee> all() {
        return (root, query, builder) -> {
            Predicate predicate = builder.conjunction();
            return predicate;
        };
    }

    public static Specification<Employee> companyId(Long companyId) {
        return (root, query, builder) -> {
            Predicate predicate = builder.equal(root.get("company").get("id"), companyId);
            return predicate;
        };
    }
    
}
