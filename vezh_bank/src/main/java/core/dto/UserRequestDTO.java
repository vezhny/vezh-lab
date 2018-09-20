package core.dto;

import vezh_bank.persistence.entity.UserRequest;

public class UserRequestDTO implements BaseDTO<UserRequest> {
    private int id;
    private UserDTO user;
    private String date;
    private String status;
    private String data;

    @Override
    public UserRequest getEntity() {
        return null;
    }
}
