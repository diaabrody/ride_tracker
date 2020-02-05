package com.pluralsight.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.pluralsight.model.Ride;
import com.pluralsight.repository.util.RowMappers.RideRowMapper;

@Repository("rideRepository")
public class RideRepositoryImpl implements RideRepository {
	
	@Autowired
	private JdbcTemplate template;
	@Autowired
	private NamedParameterJdbcTemplate namedParamTemplate ; 
	
	@Override
	public List<Ride> getRides() {
		List<Ride> rides = template.query("select * from ride", new RideRowMapper());
		return rides;
	}

	@Override
	public Ride createRide(Ride ride) {
	//	template.update("insert into ride ( name , duration ) values (?,?)", ride.getName() , ride.getDuration());
//		SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
//		List<String> columnNames = new ArrayList<>();
//		Map<String,Object> values = new HashMap<>();
//		insert.setTableName("ride");
//		columnNames.add("name");
//		columnNames.add("duration");
//		values.put("name",ride.getName());
//		values.put("duration", ride.getDuration());
//		insert.setColumnNames(columnNames);
//		insert.setGeneratedKeyName("id");
//		Number id =insert.executeAndReturnKey(values);
		
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		 template.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps =con.prepareStatement("insert into ride (name , duration) values (?,?)",new String[] {"id"} );
				ps.setString(1, ride.getName());
				ps.setInt(2, ride.getDuration());
				return ps;
			}
		}, generatedKeyHolder);
		return getRide(generatedKeyHolder.getKey().intValue());
	}
	
	@Override
	public Ride getRide(Integer number) {
		Ride ride =template.queryForObject("select * from ride where id = ?", new RideRowMapper() ,number);
		return ride;
	}

	@Override
	public Ride updateRide(Ride ride) {
		String sql = "update ride set name=? , duration = ? where id = ?";
		template.update(sql , ride.getName() , ride.getDuration() , ride.getId());
		return ride;
	}

	@Override
	public void updateRides(List<Object[]> pairs) {
		String sql = "update ride set ride_date=? where id=?";
		template.batchUpdate(sql, pairs);
	}
	
	@Override
	public void delete(Integer id) {
		Map<String , Object> paramMap = new HashMap<>();
		paramMap.put("id", id);
		namedParamTemplate.update("delete from ride where id = :id", paramMap);
	}

}
