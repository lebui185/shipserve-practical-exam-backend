package lebui.shipserve.practicaljavaexam.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Where(clause = "deleted = false")
@SQLDelete(sql =  "UPDATE company SET deleted = true WHERE id = ?")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String name;

    private String address;

    @ManyToOne(fetch = FetchType.EAGER)
    private CompanyTypeLookup type;
    
    private Boolean active;
    
    private Boolean deleted;

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
