package lebui.shipserve.practicaljavaexam.security.jwt;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoggedInUser {

    private Long id;

    private String firstName;

    private String surname;

    private String email;
    
    private String username;
    
    private Boolean active;
    
    private Set<String> roles;
    
    private Long companyId;
    
}
