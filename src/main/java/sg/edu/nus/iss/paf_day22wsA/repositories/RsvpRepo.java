package sg.edu.nus.iss.paf_day22wsA.repositories;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.paf_day22wsA.model.Rsvp;

@Repository
public class RsvpRepo {
    
    @Autowired
    private JdbcTemplate template;


    public List<Rsvp> getRsvps(){

        SqlRowSet rs = template.queryForRowSet(Queries.SQL_RETRIEVE_ALL_RSVPS);

        List<Rsvp> rsvps = new LinkedList<>();

        while (rs.next()){
            rsvps.add(Rsvp.toRsvp(rs));
        }

        return rsvps;

    }



    public List<Rsvp> getRsvps(int limit){
        
        SqlRowSet rs = template.queryForRowSet(Queries.SQL_RETRIEVE_ALL_RSVPS_LIMIT, limit);

        List<Rsvp> rsvps = new LinkedList<>();

        while (rs.next()){
            rsvps.add(Rsvp.toRsvp(rs));
        }

        return rsvps;

    }



    public Optional<List<Rsvp>> searchByEmail(String query) {
        
        SqlRowSet rs = template.queryForRowSet(Queries.SQL_SEARCH_BY_EMAIL, "%%%s%%".formatted(query));

        List<Rsvp> rsvps = new LinkedList<>();

        while (rs.next()){
            rsvps.add(Rsvp.toRsvp(rs));
        }

        if (rsvps.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(rsvps);

    }
}
