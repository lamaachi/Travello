package com.example.travel_agency_pfe.Services;

import com.example.travel_agency_pfe.Models.AppUser;
import com.example.travel_agency_pfe.Repositories.IAppUserRepository;
import com.example.travel_agency_pfe.Repositories.IClientRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IClientServiceImpl implements IClientService {

    @Autowired
    private IClientRepository iClientRepository;

    @Override
    public List<AppUser> getAllUsers() {
        return iClientRepository.getAllUsersExceptAdmin();
    }

    @Override
    public void deleteClient(String id) {
        iClientRepository.deleteById(id);
    }

    @Override
    public AppUser save(AppUser appUser) {
        return iClientRepository.save(appUser);
    }

    @Override
    public AppUser getById(String id) {
        Optional<AppUser> resUser = iClientRepository.findById(id);
        if(resUser.isPresent()){
            return resUser.get();
        }
        return null;
    }


}
