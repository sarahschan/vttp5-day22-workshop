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


    public Optional<Rsvp> searchByName(String name) {
        
        System.out.println("searching for " + name);

        SqlRowSet rs = template.queryForRowSet(Queries.SQL_SEARCH_BY_NAME, "%%%s%%".formatted(name));

        if (!rs.next()){    // No rsvp found
            return Optional.empty();
        
        } else {
            return Optional.of(Rsvp.toRsvp(rs));
        }

    }


    public void insertOrUpdateRsvp(Rsvp rsvp){
        template.update(Queries.SQL_INSERT_OR_UPDATE,
                        rsvp.getName(),
                        rsvp.getEmail(),
                        rsvp.getPhone(),
                        rsvp.getConfirmationDate(),
                        rsvp.getComments());
    }
}
