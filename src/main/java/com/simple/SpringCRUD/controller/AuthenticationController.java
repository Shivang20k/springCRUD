package com.simple.SpringCRUD.controller;

import com.simple.SpringCRUD.model.JwtRequest;
import com.simple.SpringCRUD.model.JwtResponse;
import com.simple.SpringCRUD.model.Passenger;
import com.simple.SpringCRUD.repository.PassengerRepository;
import com.simple.SpringCRUD.security.JwtHelper;
import com.simple.SpringCRUD.service.CustomSecurityUserDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private CustomSecurityUserDetailService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    private final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
        this.doAuthenticate(request.getEmail(), request.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.jwtHelper.generateToken(userDetails);
        JwtResponse response = new JwtResponse(token, userDetails.getUsername());
        logger.info("JWT Response created successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createPassenger(@RequestBody Passenger passenger) {
        Passenger savePassenger = passenger;
        savePassenger.setPassword(new BCryptPasswordEncoder().encode(passenger.getPassword()));
        passengerRepository.save(savePassenger);
        return new ResponseEntity<>("Creation successfully", HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            authenticationManager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }
    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }
}
