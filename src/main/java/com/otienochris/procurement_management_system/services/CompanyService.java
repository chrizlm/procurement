package com.otienochris.procurement_management_system.services;

import com.otienochris.procurement_management_system.models.Company;
import com.otienochris.procurement_management_system.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepo;

    public List<Company> getAllCompanies(){
        return companyRepo.findAll();
    }

    public Optional<Company> getCompanyByKraPin(String kra){
        return companyRepo.findByKraPin(kra);
    }

    public Optional<Company> getByid(Long id){
        return companyRepo.findById(id);
    }

    /*
    * saves a company checking to ensure that their is no other company with the same kra pin
    * entered. It then returns a list of all available companies
    *
    * */
    public List<Company> addCompany(Company company){
        if (companyRepo.findByKraPin(company.getKraPin()).isEmpty()){
            companyRepo.save(company); // saves a company with a unique kra pin
            return companyRepo.findAll();
        }
        return companyRepo.findAll();
    }

    public List<Company> deleteCompanyByKra(String kra){
        if (companyRepo.findByKraPin(kra).isPresent()){
            companyRepo.delete(companyRepo.findByKraPin(kra).get());
        }
        return companyRepo.findAll();
    }

    public List<Company> deleteCompanyById(Long id){
        if (companyRepo.findById(id).isPresent()){
            companyRepo.delete(companyRepo.findById(id).get());
            return companyRepo.findAll();
        }
        return companyRepo.findAll();
    }

    public List<Company> deleteCompany(Company company){
        if(companyRepo.findById(company.getCompanyId()).isPresent()){
            companyRepo.delete(company);
            return companyRepo.findAll();
        }
        return companyRepo.findAll();
    }

//    receive an object of company from the view
    public Company updateCompany(Company company){
        if (companyRepo.findById(company.getCompanyId()).isPresent()) {
            Company oldCompany = companyRepo.findById(company.getCompanyId()).get();
            oldCompany.setDescription(company.getDescription());
            oldCompany.setKraPin(company.getKraPin());
            oldCompany.setName(company.getName());

            return companyRepo.save(oldCompany);
        }
        return null;
    }
}
