package sg.edu.nus.iss.paf_day22wsA.model;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class Rsvp {
    
    private String name;
    private String email;
    private String phone;
    private String confirmationDate;
    private String comments;
    
    
    public Rsvp() {
    }


    public static Rsvp toRsvp(SqlRowSet rs){

        Rsvp rsvp = new Rsvp();
            rsvp.setName(rs.getString("name"));
            rsvp.setEmail(rs.getString("email"));
            rsvp.setPhone(String.valueOf(rs.getInt("phone")));
            rsvp.setConfirmationDate(String.valueOf(rs.getDate("confirmation_date")));
            rsvp.setComments(rs.getString("comments"));

        return rsvp;
    }

    
    @Override
    public String toString() {
        return "Rsvp [name=" + name + ", email=" + email + ", phone=" + phone + ", confirmationDate=" + confirmationDate
                + ", comments=" + comments + "]";
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getConfirmationDate() {
        return confirmationDate;
    }

    public void setConfirmationDate(String confirmationDate) {
        this.confirmationDate = confirmationDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

}