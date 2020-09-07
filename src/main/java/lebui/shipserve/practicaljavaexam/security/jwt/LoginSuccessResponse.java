package lebui.shipserve.practicaljavaexam.security.jwt;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginSuccessResponse {

    private String token;
    
    private LoggedInUser user;

}
