package lebui.shipserve.practicaljavaexam.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lebui.shipserve.practicaljavaexam.Constant;
import lebui.shipserve.practicaljavaexam.dto.CompanyDTO;
import lebui.shipserve.practicaljavaexam.dto.CompanySaveDTO;
import lebui.shipserve.practicaljavaexam.dto.CompanyTypeDTO;
import lebui.shipserve.practicaljavaexam.entity.Company;
import lebui.shipserve.practicaljavaexam.exception.NotFoundException;
import lebui.shipserve.practicaljavaexam.mapper.CompanyMapper;
import lebui.shipserve.practicaljavaexam.repository.CompanyRepository;
import lebui.shipserve.practicaljavaexam.repository.CompanyTypeLookupRepository;

@Service
@Transactional
public class CompanyService {
    
    @Autowired
    private CompanyRepository companyRepository;
    
    @Autowired
    private CompanyTypeLookupRepository companyTypeLookupRepository;
    
    @Autowired
    private CompanyMapper companyMapper;
    
    @Autowired
    private AuthenticationService authenticationService;
    
    public CompanyDTO findCompanyById(Long companyId) {
        Company company = this.companyRepository
            .findById(companyId)
            .orElseThrow(() -> new NotFoundException("Company ID is not found: " + companyId));
        if (this.authenticationService.hasAnyRoles(Constant.USER_ROLE.COMPANY_ADMIN, Constant.USER_ROLE.USER)
            && !this.authenticationService.isLoggedInUserHasPermissionOnCompany(company)
        ) {
            throw new AccessDeniedException("");
        }
        
        return this.companyMapper.mapCompanyToCompanyDTO(company);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public List<CompanyDTO> findAllCompanies() {
        return this.companyRepository.findAll().stream()
            .map(company -> this.companyMapper.mapCompanyToCompanyDTO(company))
            .collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public CompanyDTO createCompany(CompanySaveDTO companySaveDTO) {
        Company newCompany = new Company();
        saveCompanySaveDTOToCompany(companySaveDTO, newCompany);
        newCompany = this.companyRepository.save(newCompany);
        
        return this.companyMapper.mapCompanyToCompanyDTO(newCompany);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'COMPANY_ADMIN')")
    public CompanyDTO updateCompany(Long companyId, CompanySaveDTO companySaveDTO) {
        Company updatingCompany = this.companyRepository
            .findById(companyId)
            .orElseThrow(() -> new NotFoundException("Company ID is not found: " + companyId));
        if (this.authenticationService.hasAnyRoles(Constant.USER_ROLE.COMPANY_ADMIN)
            && !this.authenticationService.isLoggedInUserHasPermissionOnCompany(updatingCompany)
        ) {
            throw new AccessDeniedException("");
        }
        
        saveCompanySaveDTOToCompany(companySaveDTO, updatingCompany);
        
        return this.companyMapper.mapCompanyToCompanyDTO(updatingCompany);
    }

    private void saveCompanySaveDTOToCompany(CompanySaveDTO companySaveDTO, Company company) {
        company.setName(companySaveDTO.getName());
        company.setAddress(companySaveDTO.getAddress());
        company.setActive(companySaveDTO.getActive());
        
        if (companySaveDTO.getTypeId() != null) {
            company.setType(this.companyTypeLookupRepository.getOne(companySaveDTO.getTypeId()));
        }
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public void deleteCompany(Long companyId) {
        this.companyRepository.deleteById(companyId);
    }

    public List<CompanyTypeDTO> findAllCompanyTypes() {
        return this.companyTypeLookupRepository.findAll().stream()
            .map(this.companyMapper::mapCompanyTypeLookupToCompanyTypeDTO)
            .collect(Collectors.toList());
    }

}