package com.flybook.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.flybook.collection.TravelRoute;

@Repository
public interface TravelRouteRepository extends MongoRepository<TravelRoute, String> {

	@Query("{'$and' : [{'sourceCity.cityCode' : ?0 },{'destinationCity.cityCode' :?1}]}")
	public List<TravelRoute> findBySourceAndDestination(String soruce,String destination);
	
	@Query("{'flightId' : { '$in' : [ ?0 ]}}")
	public List<TravelRoute> findByFlightId(String flightId);
	
}
