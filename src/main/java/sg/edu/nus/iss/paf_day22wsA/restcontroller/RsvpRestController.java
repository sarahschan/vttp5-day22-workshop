package sg.edu.nus.iss.paf_day22wsA.restcontroller;

import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
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
    public ResponseEntity<String> searchByName(@RequestParam(name = "q") String name){

        HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");
        

        Optional<Rsvp> rsvpOpt = rsvpService.searchByName(name);

        if (rsvpOpt.isEmpty()){         // No rsvp found
            JsonObject error = Json.createObjectBuilder()
                .add("error", "No RSVP records found for query: " + name)
                .build();

            return ResponseEntity.status(404).headers(headers).body(error.toString());
        }

        Rsvp rsvp = rsvpOpt.get();      // Rsvp found
        System.out.println(rsvp);
        JsonObject rsvpJson = rsvpSerializer.toRsvpJsonObject(rsvp);

        return ResponseEntity.status(200).headers(headers).body(rsvpJson.toString());

    }


    @PostMapping(path = "/rsvp", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addNewRsvp(@RequestBody String formEntry){

        JsonReader jsonReader = Json.createReader(new StringReader(formEntry));
        JsonObject rsvpJson = jsonReader.readObject();


        return null;
    }

}