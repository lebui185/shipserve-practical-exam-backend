package lebui.shipserve.practicaljavaexam.mapper;

import org.springframework.stereotype.Component;

import lebui.shipserve.practicaljavaexam.dto.CompanyDTO;
import lebui.shipserve.practicaljavaexam.dto.CompanyTypeDTO;
import lebui.shipserve.practicaljavaexam.entity.Company;
import lebui.shipserve.practicaljavaexam.entity.CompanyTypeLookup;

@Component
public class CompanyMapper {

    public CompanyDTO mapCompanyToCompanyDTO(Company company) {
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setId(company.getId());
        companyDTO.setName(company.getName());
        companyDTO.setAddress(company.getAddress());
        
        if (company.getType() != null) {
            companyDTO.setType(mapCompanyTypeLookupToCompanyTypeDTO(company.getType()));
        }
        
        companyDTO.setActive(company.getActive());
        
        return companyDTO;
    }
    
    public CompanyTypeDTO mapCompanyTypeLookupToCompanyTypeDTO(CompanyTypeLookup companyTypeLookup) {
        CompanyTypeDTO companyTypeDTO = new CompanyTypeDTO();
        companyTypeDTO.setId(companyTypeLookup.getId());
        companyTypeDTO.setValue(companyTypeLookup.getValue());
        
        return companyTypeDTO;
    }

}
