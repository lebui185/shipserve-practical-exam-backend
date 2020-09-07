package lebui.shipserve.practicaljavaexam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import lebui.shipserve.practicaljavaexam.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String roleString);

}
