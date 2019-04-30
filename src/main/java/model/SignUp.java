package model;

/**
 * Date:4/29/19
 * Time:22:55
 */
public class SignUp {
    private String firstName,lastName,address,city,state,zip,phone,ssn,role,username,password;
    public SignUp(){};
    public SignUp(String initUsername,String initPassowrd,String initFirstName,
                  String initLastName ,String initAddress,
                  String initCity,String initState,String initZip,
                  String initPhone,String initSSN,String initRole){
        username=initUsername;
        password=initPassowrd;
        firstName = initFirstName;
        lastName = initLastName;
        address = initAddress;
        city = initCity;
        state = initState;
        zip=initZip;
        phone = initPhone;
        ssn = initSSN;
        role = initRole;
    }
    //setter getter

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getRole() {
        return role;
    }

    public String getSsn() {
        return ssn;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
