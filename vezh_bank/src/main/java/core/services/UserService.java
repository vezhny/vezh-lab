package core.services;

import core.dto.UserDTO;
import org.springframework.stereotype.Service;
import vezh_bank.persistence.DataBaseService;
import vezh_bank.persistence.entity.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private DataBaseService dataBaseService;

    public UserService(DataBaseService dataBaseService) {
        this.dataBaseService = dataBaseService;
    }

    public void addUser(UserDTO userDTO) {
        User user = new User(userDTO.getLogin(), userDTO.getEncryptedPassword(),
                userDTO.getRole().getEntity(), userDTO.getData().toString(),
                userDTO.getConfig().toString(), userDTO.getAttemptsToSignIn());
        dataBaseService.getUserDao().insert(user);
    }

    public List<UserDTO> getUsers(List<User> users) {
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            userDTOS.add(new UserDTO(user, dataBaseService.getRoleDao().get(user.getRole())));
        }
        return userDTOS;
    }

}
