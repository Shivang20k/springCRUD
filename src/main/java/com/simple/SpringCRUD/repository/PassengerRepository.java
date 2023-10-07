package com.simple.SpringCRUD.repository;

import com.simple.SpringCRUD.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    public Optional<Passenger> findByMail(String email);
}
