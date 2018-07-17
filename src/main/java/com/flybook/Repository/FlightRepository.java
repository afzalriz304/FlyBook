package com.flybook.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.flybook.collection.Flights;

@Repository
public interface FlightRepository extends MongoRepository<Flights, String>{

}
