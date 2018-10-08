package core.dto;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import core.json.UserRequestData;
import vezh_bank.constants.DatePatterns;
import vezh_bank.enums.UserRequestStatus;
import vezh_bank.persistence.entity.UserRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UserRequestDTO implements BaseDTO<UserRequest> {
    @Expose
    private int id;

    @Expose
    private UserDTO user;

    @Expose
    private String date;

    @Expose
    private UserRequestStatus status;

    @Expose
    private UserRequestData data;

    private transient UserRequest userRequest;

    public UserRequestDTO(UserDTO user, String detail) {
        this.user = user;
        this.date = new SimpleDateFormat(DatePatterns.DEFAULT_DATE_PATTERN).format(new Date());
        this.status = UserRequestStatus.OPEN;
        this.data = new UserRequestData(user.getId(), detail);
    }

    public UserRequestDTO(UserRequest userRequest, UserDTO userDTO) {
        this.id = userRequest.getId();
        this.user = userDTO;
        this.date = userRequest.getDate();
        this.status = UserRequestStatus.valueOf(userRequest.getStatus());
        this.data = new Gson().fromJson(userRequest.getData(), UserRequestData.class);
        this.userRequest = userRequest;
    }

    public void setStatus(UserRequestStatus status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public UserDTO getUser() {
        return user;
    }

    public String getDate() {
        return date;
    }

    public UserRequestStatus getStatus() {
        return status;
    }

    public UserRequestData getData() {
        return data;
    }

    @Override
    public UserRequest getEntity() {
        return userRequest;
    }
}
