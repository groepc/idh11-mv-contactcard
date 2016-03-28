package miekevadiem.edu.contactcard;

public class Contact {

    public String firstName;
    public String lastName;
    public String email;
    public String imageUrl;

    public Contact(String firstName, String lastName, String email) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setImageUrl(null);
    }

    public Contact() {

    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFullName() {
        return this.getFirstName() + " " + this.getLastName();
    }

    @Override
    public String toString() {
        return "Contact{" +
                "imageUrl='" + imageUrl + '\'' +
                ", email='" + email + '\'' +
                ", first='" + firstName + '\'' +
                ", last='" + lastName + '\'' +
                '}';
    }
}
