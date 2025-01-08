package sg.edu.nus.iss.paf_day22wsA.restcontroller;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.paf_day22wsA.model.Rsvp;
import sg.edu.nus.iss.paf_day22wsA.serializer.RsvpSerializer;
import sg.edu.nus.iss.paf_day22wsA.service.RsvpService;

@RestController
@RequestMapping("/api")
public class RsvpRestController {
    
    @Autowired
    private RsvpService rsvpService;

    @Autowired
    private RsvpSerializer rsvpSerializer;


    @GetMapping(path = "/rsvps", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllRsvps(){
        
        HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");

        try {
            
            List<JsonObject> allRsvps = rsvpService.getAllRsvpsJsonObject();

            JsonArrayBuilder rsvpsArrayBuilder = Json.createArrayBuilder();
            
            for (JsonObject rsvpJsonObject : allRsvps){
                rsvpsArrayBuilder.add(rsvpJsonObject);     
            }

            return ResponseEntity.status(200).headers(headers).body(rsvpsArrayBuilder.build().toString());

        } catch (Exception e){

            JsonObject errorMsg = Json.createObjectBuilder()
                .add("error", e.getMessage())
                .build();

            return ResponseEntity.status(500).headers(headers).body(errorMsg.toString());

        }
    }


    @GetMapping(path = "/rsvp", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> searchByEmail(@RequestParam(name = "q") String query){

        HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");
        

        Optional<List<Rsvp>> rsvpsOpt = rsvpService.searchByEmail(query);

        if (rsvpsOpt.isEmpty()){
            JsonObject error = Json.createObjectBuilder()
                .add("error", "No RSVP records found for query: " + query)
                .build();

            return ResponseEntity.status(404).headers(headers).body(error.toString());
        }
        
        List<Rsvp> rsvps = rsvpsOpt.get();
        JsonArrayBuilder rsvpsArrayBuilder = Json.createArrayBuilder();

        for (Rsvp rsvp : rsvps){
            JsonObject rsvpJsonObject = rsvpSerializer.toRsvpJsonObject(rsvp);
            rsvpsArrayBuilder.add(rsvpJsonObject);
        }

        return ResponseEntity.status(200).headers(headers).body(rsvpsArrayBuilder.build().toString());

    }
}