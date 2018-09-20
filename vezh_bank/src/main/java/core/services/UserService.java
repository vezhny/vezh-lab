package core.services;

import core.dto.UserDTO;
import org.springframework.stereotype.Service;
import vezh_bank.persistence.DataBaseService;

@Service
public class UserService {
    private DataBaseService dataBaseService;

    public UserService(DataBaseService dataBaseService) {
        this.dataBaseService = dataBaseService;
    }

    public void addUser(UserDTO userDTO) {
        dataBaseService.getUserDao().insert(userDTO.getEntity());
    }
}
