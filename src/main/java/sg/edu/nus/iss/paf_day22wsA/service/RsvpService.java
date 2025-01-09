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


    public Optional<Rsvp> searchByEmail(String searchPhrase){
        return rsvpRepo.searchByEmail(searchPhrase);
    }


    public void insertOrUpdateRsvp(JsonObject rsvpJson){

        rsvpRepo.insertOrUpdateRsvp(rsvpJson);
    }


    public boolean rsvpExists(String email) {
        
        Optional<Rsvp> foundRsvp = searchByEmail(email);

        if (foundRsvp.isEmpty()){
            return false;
        }

        return true;
    }


    public void editExistingRsvp(String existingEmail, Rsvp rsvp){
        rsvpRepo.editExistingRsvp(existingEmail, rsvp);
    }


    public int countRsvps(){
        return rsvpRepo.countRsvps();
    }
}
