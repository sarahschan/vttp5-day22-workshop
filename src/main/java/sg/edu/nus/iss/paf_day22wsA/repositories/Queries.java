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


    public static final String SQL_SEARCH_BY_NAME = 
        """
            select * from rsvp
            where name like ?
        """;

    
    public static final String SQL_INSERT_OR_UPDATE = 
        """
            insert into rsvp
                (name, email, phone, confirmation_date, comments)
            values
                (?, ?, ?, ?, ?)
            as new_rsvp
            on duplicate key update
                name = new_rsvp.name,
                phone = new_rsvp.phone,
                confirmation_date = new_rsvp.confirmation_date,
                comments = new_rsvp.comments
        """;
}
