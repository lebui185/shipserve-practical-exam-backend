package lebui.shipserve.practicaljavaexam.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Where(clause = "deleted = false")
@DynamicUpdate
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String firstName;

    private String surname;

    private String email;
    
    private String username;
    
    private String password;

    private Boolean active;

    private Boolean deleted;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "employee_role",
        joinColumns = @JoinColumn(name = "employee_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
    
    @PrePersist
    public void prePersist() {
        if (this.active == null) {
            this.active = Boolean.TRUE;
        }
        if (this.deleted == null) {
            this.deleted = Boolean.FALSE;
        }
    }
}
