package lebui.shipserve.practicaljavaexam;

public class Constant {

    public static final class SECURITY {
        public static final String TOKEN_HEADER = "Authorization";
        public static final String TOKEN_PREFIX = "Bearer ";
        public static final String JWT_SECRET = "LH+gAPkC6/6lT90WhLU9QTYp2qQ6UxRaCB1IQBlomdk=";
        public static final String JWT_LOGIN_URL = "/api/login";
    }
    
    public static final class JWT_CLAIM {
        public static final String ROLES = "roles";
        public static final String TOKEN_TYPE = "JWT";
        public static final String TYPE = "typ";
    }
    
    public static final class USER_ROLE {
        public static final String SUPER_ADMIN = "SUPER_ADMIN";
        public static final String COMPANY_ADMIN = "COMPANY_ADMIN";
        public static final String USER = "USER";
    }
    
}
