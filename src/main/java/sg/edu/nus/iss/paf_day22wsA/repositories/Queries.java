package sg.edu.nus.iss.paf_day22wsA.repositories;

public class Queries {
    
    public static final String SQL_RETRIEVE_ALL_RSVPS =
        """
            select * from rsvp    
        """;


    public static final String SQL_RETRIEVE_ALL_RSVPS_LIMIT =
        """
            select * from rsvp limit ?
        """;


    public static final String SQL_SEARCH_BY_EMAIL = 
        """
            select * from rsvp
            where email like ?
        """;
}
