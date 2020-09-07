package lebui.shipserve.practicaljavaexam.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CompanyTypeLookup {

    @Id
    private Long id;

    @EqualsAndHashCode.Include
    private String value;

}
