package lebui.shipserve.practicaljavaexam.dto;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeSaveDTO {

    private String firstName;

    private String surname;

    private String email;
    
    private String username;
    
    private String password;

    private Boolean active;

    private Long companyId;
    
    private Set<String> roles = new HashSet<>();
    
}
