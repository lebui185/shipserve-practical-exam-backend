package lebui.shipserve.practicaljavaexam.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanySaveDTO {

    private String name;

    private String address;

    private Long typeId;
    
    private Boolean active;
    
}
