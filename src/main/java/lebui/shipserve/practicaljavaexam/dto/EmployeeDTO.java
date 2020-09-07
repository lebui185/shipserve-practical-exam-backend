package lebui.shipserve.practicaljavaexam.dto;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDTO {

    private Long id;

    private String firstName;

    private String surname;

    private String email;
    
    private String username;
    
    private Boolean active;
    
    private Long companyId;
    
    private Set<String> roles;
    
}
