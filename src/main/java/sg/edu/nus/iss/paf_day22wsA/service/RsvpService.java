package sg.edu.nus.iss.paf_day22wsA.service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.JsonObject;
import sg.edu.nus.iss.paf_day22wsA.model.Rsvp;
import sg.edu.nus.iss.paf_day22wsA.repositories.RsvpRepo;
import sg.edu.nus.iss.paf_day22wsA.serializer.RsvpSerializer;

@Service
public class RsvpService {
    
    @Autowired
    private RsvpRepo rsvpRepo;

    @Autowired
    private RsvpSerializer rsvpSerializer;


    public List<Rsvp> getAllRsvps(){
        return rsvpRepo.getRsvps();
    }


    public List<Rsvp> getAllRsvps(int limit){
        return rsvpRepo.getRsvps(limit);
    }


    public List<JsonObject> getAllRsvpsJsonObject(){

        List<Rsvp> allRsvps = getAllRsvps();

        List<JsonObject> allRsvpsJsonObject = new LinkedList<>();

        for (Rsvp rsvp : allRsvps) {
            JsonObject rsvpJsonObject = rsvpSerializer.toRsvpJsonObject(rsvp);
            allRsvpsJsonObject.add(rsvpJsonObject);
        }

        return allRsvpsJsonObject;
    }


    public Optional<List<Rsvp>> searchByEmail(String query){

        Optional<List<Rsvp>> foundRsvps = rsvpRepo.searchByEmail(query);

        return foundRsvps;

    }

}
