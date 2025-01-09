package sg.edu.nus.iss.paf_day22wsA.restcontroller;

import java.io.StringReader;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<String> searchByEmail(@RequestParam(name = "q") String searchPhrase){

        HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");
        

        Optional<Rsvp> rsvpOpt = rsvpService.searchByEmail(searchPhrase);

        if (rsvpOpt.isEmpty()){         // No rsvp found
            JsonObject error = Json.createObjectBuilder()
                .add("error", "No RSVP records found for query: " + searchPhrase)
                .build();

            return ResponseEntity.status(404).headers(headers).body(error.toString());
        }

        Rsvp rsvp = rsvpOpt.get();      // Rsvp found
        JsonObject rsvpJson = rsvpSerializer.toRsvpJsonObject(rsvp);

        return ResponseEntity.status(200).headers(headers).body(rsvpJson.toString());

    }


    @PostMapping(path = "/rsvp", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addNewRsvp(@RequestBody String formEntry){

        JsonReader jsonReader = Json.createReader(new StringReader(formEntry));
        JsonObject rsvpJson = jsonReader.readObject();

        try {
            
            rsvpService.insertOrUpdateRsvp(rsvpJson);

            return ResponseEntity.status(201).body(null);

        } catch (Exception e){

            System.out.println(e.getCause());
            e.printStackTrace();

            return ResponseEntity.status(500).body(null);
        }

    }


    @PutMapping(path = "/rsvp/{email}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> editExistingRsvp(@PathVariable String email, @RequestParam MultiValueMap<String, String> data){

        try {
                        
            if (!rsvpService.rsvpExists(email)){
                JsonObject notFound = Json.createObjectBuilder()
                                        .add("forFoundMsg", "RSVP for email " + email + " not found")
                                        .build();

                return ResponseEntity.status(404).body(notFound.toString());
            }

        } catch (Exception e){

            JsonObject errorMessage = Json.createObjectBuilder()
                                        .add("errorMsg", "Error during lookup for existing RSVP")
                                        .build();

            return ResponseEntity.status(500).body(errorMessage.toString());
        }


        try {

            Rsvp rsvp = new Rsvp();
                rsvp.setEmail(data.getFirst("email"));
                rsvp.setPhone(data.getFirst("phone"));
                rsvp.setConfirmationDate(data.getFirst("confirmDate"));
                rsvp.setComments(data.getFirst("comments"));

            rsvpService.editExistingRsvp(email, rsvp);

            JsonObject successMessage = Json.createObjectBuilder()
                                            .add("successMsg", "RSVP for email " + email + " updated")
                                            .build();

            return ResponseEntity.status(201).body(successMessage.toString());

        } catch (Exception e) {

            System.out.println(e.getCause() + ": " + e.getStackTrace());

            JsonObject errorMessage = Json.createObjectBuilder()
                                        .add("errorMsg", "Error during update for existing RSVP")
                                        .build();
            
            return ResponseEntity.status(500).body(errorMessage.toString());
        }

    }


}