package lebui.shipserve.practicaljavaexam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import lebui.shipserve.practicaljavaexam.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}
