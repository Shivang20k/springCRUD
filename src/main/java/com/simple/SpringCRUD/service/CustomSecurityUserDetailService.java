package com.simple.SpringCRUD.service;

import com.simple.SpringCRUD.exception.ResourceNotFoundException;
import com.simple.SpringCRUD.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomSecurityUserDetailService implements UserDetailsService {

    @Autowired
    PassengerRepository passengerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return passengerRepository.findByMail(username)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger does not exist by UserName" + username));
    }
}
