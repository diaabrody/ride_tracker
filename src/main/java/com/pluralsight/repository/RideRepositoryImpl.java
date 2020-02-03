package com.pluralsight.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.pluralsight.model.Ride;
import com.pluralsight.repository.util.RowMappers.RideRowMapper;

@Repository("rideRepository")
public class RideRepositoryImpl implements RideRepository {
	
	@Autowired
	private JdbcTemplate template;
	

	
	
	@Override
	public List<Ride> getRides() {
		List<Ride> rides = template.query("select * from ride", new RideRowMapper());
		return rides;
	}




	@Override
	public Ride createRide(Ride ride) {
	//	template.update("insert into ride ( name , duration ) values (?,?)", ride.getName() , ride.getDuration());
		SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
		List<String> columnNames = new ArrayList<>();
		Map<String,Object> values = new HashMap<>();
		insert.setTableName("ride");
		columnNames.add("name");
		columnNames.add("duration");
		values.put("name",ride.getName());
		values.put("duration", ride.getDuration());
		insert.setColumnNames(columnNames);
		insert.setGeneratedKeyName("id");
		Number id =insert.executeAndReturnKey(values);
		System.out.println(id);
		return null;
	}
	
}
