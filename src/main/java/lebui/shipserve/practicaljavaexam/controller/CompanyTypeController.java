package lebui.shipserve.practicaljavaexam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lebui.shipserve.practicaljavaexam.dto.CompanyTypeDTO;
import lebui.shipserve.practicaljavaexam.service.CompanyService;

@RestController
@RequestMapping("/api/company-types")
public class CompanyTypeController {
    
    @Autowired
    private CompanyService companyService;
    
    @GetMapping(produces = "application/json")
    public List<CompanyTypeDTO> findAllCompanyTypes() {
        return this.companyService.findAllCompanyTypes();
    }

}
