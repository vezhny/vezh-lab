package core.json;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

public class UserData {
    @Expose
    private String firstName;

    @Expose
    private String middleName;

    @Expose
    private String patronymic;

    @Expose
    private String birthDate;

    @Expose
    private UserAddress address;

    @Expose
    private String contactNumber;

    @Expose
    private String email;

    public UserData(String firstName, String middleName, String patronymic, String birthDate,
                    UserAddress address, String contactNumber, String email) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.patronymic = patronymic;
        this.birthDate = birthDate;
        this.address = address;
        this.contactNumber = contactNumber;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public UserAddress getAddress() {
        return address;
    }

    public void setAddress(UserAddress address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
