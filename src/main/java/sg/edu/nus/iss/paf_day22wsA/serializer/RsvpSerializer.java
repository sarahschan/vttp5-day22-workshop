package sg.edu.nus.iss.paf_day22wsA.serializer;

import org.springframework.stereotype.Component;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.paf_day22wsA.model.Rsvp;

@Component
public class RsvpSerializer {
    
    public JsonObject toRsvpJsonObject(Rsvp rsvp){
        
        JsonObject rsvpJsonObject = Json.createObjectBuilder()
            .add("email", rsvp.getEmail())
            .add("phone", rsvp.getPhone())
            .add("confirmDate", rsvp.getConfirmationDate())
            .add("comments", rsvp.getComments())
            .build();

        return rsvpJsonObject;
    }
}
