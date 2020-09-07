package lebui.shipserve.practicaljavaexam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lebui.shipserve.practicaljavaexam.dto.CompanyDTO;
import lebui.shipserve.practicaljavaexam.dto.CompanySaveDTO;
import lebui.shipserve.practicaljavaexam.service.CompanyService;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {
    
    @Autowired
    private CompanyService companyService;
    
    @GetMapping(value = "/{companyId}", produces = "application/json")
    public CompanyDTO findCompanyById(@PathVariable Long companyId) {
        return this.companyService.findCompanyById(companyId);
    }
    
    @GetMapping(produces = "application/json")
    public List<CompanyDTO> findAllCompanies() {
        return this.companyService.findAllCompanies();
    }
    
    @PostMapping(produces = "application/json", consumes = "application/json")
    public CompanyDTO createCompany(@RequestBody CompanySaveDTO companySaveDTO) {
        return this.companyService.createCompany(companySaveDTO);
    }

    @PutMapping(value = "/{companyId}", produces = "application/json", consumes = "application/json")
    public CompanyDTO updateCompany(@PathVariable Long companyId, @RequestBody CompanySaveDTO companySaveDTO) {
        return this.companyService.updateCompany(companyId, companySaveDTO);
    }
    
    @DeleteMapping(value = "/{companyId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCompany(@PathVariable Long companyId) {
        this.companyService.deleteCompany(companyId);
    }
    
}
