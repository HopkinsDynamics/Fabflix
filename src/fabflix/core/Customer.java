/* ====================================================================================================
   DATE:      06 APR 2018
   AUTHORS:   Kelly McKeown   David Hopkins
   UCInetIDs: KPMCKEOW        HOPKINSD
   ID#'s:     42806243        70404050
   CLASS:     CS 122B
   PROJECT:   #2
   ----------------------------------------------------------------------------------------------------
   DESCRIPTION:
   ====================================================================================================
 */

package fabflix.core;

public class Customer {
    private int id;
    private String firstName;
    private String lastName;
    private String ccId;
    private String address;
    private String email;
    private ShoppingCart sc;
    private CreditCard cc;

    public Customer() {

    }

    public Customer(String firstName, String lastName, String address, String email, CreditCard cc) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.cc = cc;
    }

    public Customer(int id, String firstName, String lastName, String ccId, String address, String email, ShoppingCart sc, CreditCard cc) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.ccId = ccId;
        this.address = address;
        this.email = email;
        this.sc = sc;
        this.cc = cc;
    }

    /* Getters and Setters */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCcId() {
        return ccId;
    }

    public void setCcId(String ccId) {
        this.ccId = ccId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ShoppingCart getSc() {
        return sc;
    }

    public void setSc(ShoppingCart sc) {
        this.sc = sc;
    }

    public CreditCard getCc() {
        return cc;
    }

    public void setCc(CreditCard cc) {
        this.cc = cc;
    }
}
