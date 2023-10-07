package com.simple.SpringCRUD.controller;

import com.simple.SpringCRUD.exception.ResourceNotFoundException;
import com.simple.SpringCRUD.model.Passenger;
import com.simple.SpringCRUD.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class APIController {

    @Autowired
    private PassengerRepository passengerRepository;

    @GetMapping("/passenger")
    private ResponseEntity<List<Passenger>> getAllPassengers() {
        return new ResponseEntity<>(passengerRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/passenger/{id}")
    private ResponseEntity<Passenger> getPassengerById(@PathVariable long id) {
        Passenger passenger;
        try {
            passenger = passengerRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Passenger does not exist by ID" + id));
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new Passenger(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(passenger, HttpStatus.FOUND);
    }

    @PostMapping("/passenger")
    private ResponseEntity<String> createPassenger(@RequestBody Passenger passenger) {
         passengerRepository.save(passenger);
         return new ResponseEntity<>("Creation successfully", HttpStatus.OK);
    }

    @PutMapping("/passenger/update/{id}")
    private ResponseEntity<Passenger> updatePassenger(@PathVariable long id, @RequestBody Passenger passenger) {
        Passenger ps =  passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger does not exist by ID" + id));
        ps.setMail(passenger.getMail());
        ps.setName(passenger.getName());
        ps.setPhone(passenger.getPhone());
        ps.setPassword(passenger.getPassword());
        passengerRepository.save(ps);
        return new ResponseEntity<>(ps, HttpStatus.OK);
    }

    @DeleteMapping("/passenger/{id}")
    private ResponseEntity<String> deletePassenger(@PathVariable long id) {
        passengerRepository.deleteById(id);
        return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/current-user")
    public String getLoggedInUserDetails(Principal principal) {
        return principal.getName();
    }
}
