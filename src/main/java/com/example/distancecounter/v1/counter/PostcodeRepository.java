package com.example.distancecounter.v1.counter;

import com.example.distancecounter.common.models.Location;
import com.example.distancecounter.v1.counter.dtos.req.AddPostcodeRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class PostcodeRepository {
    private final JdbcTemplate jdbcTemplate;

    public PostcodeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

     int addLocation(AddPostcodeRequest newLocation) {
         String sql = "INSERT INTO postcodelatlng (postcode, latitude, longitude) VALUES (?, ?, ?)";
         try {
            return jdbcTemplate.update(sql, newLocation.getPostcode(), newLocation.getLatitude(), newLocation.getLongitude());
         } catch(Exception ex) {
             return -1;
         }
    }

    Location getLocationByPostcode(String postcode){
        String sql = "SELECT * FROM postcodelatlng WHERE postcode = ?";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new Location(
                rs.getString("postcode"),
                rs.getDouble("latitude"),
                rs.getDouble("longitude")
            ), postcode);
        } catch (Exception ex) {
            return null;
        }
    }
}
