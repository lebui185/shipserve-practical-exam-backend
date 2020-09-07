package lebui.shipserve.practicaljavaexam.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDTO {
    
    private Long id;

    private String name;

    private String address;

    private CompanyTypeDTO type;

    private Boolean active;

}
