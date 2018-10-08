package core.services;

import core.dto.EventDTO;
import core.dto.UserDTO;
import core.json.EventData;
import core.json.UserConfig;
import core.json.UserData;
import org.springframework.stereotype.Service;
import vezh_bank.constants.EventDescriptions;
import vezh_bank.enums.EventType;
import vezh_bank.persistence.DataBaseService;
import vezh_bank.persistence.entity.User;
import vezh_bank.util.Encryptor;

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

    public void updateUser(User user, String password, UserData userData, UserConfig userConfig, int attemptsToSignIn,
                           String lastSignIn, boolean blocking) {
        Encryptor encryptor = new Encryptor();
        user.setPassword(encryptor.encrypt(password));
        user.setData(userData.toString());
        user.setConfig(userConfig.toString());
        user.setAttemptsToSignIn(attemptsToSignIn);
        user.setLastSignIn(lastSignIn);
        user.setBlocked(blocking);
        dataBaseService.getUserDao().update(user);
    }

    public void blockUser(User user, EventService eventService) {
        user.setBlocked(true);
        dataBaseService.getUserDao().update(user);
        eventService.addEvent(new EventDTO(EventType.USER_BLOCKED,
                new EventData(EventDescriptions.userHasBeenBlocked(user.getLogin()))));
    }

    public void unblockUser(User user, EventService eventService) {
        user.setBlocked(false);
        dataBaseService.getUserDao().update(user);
        eventService.addEvent(new EventDTO(EventType.USER_UNBLOCKED,
                new EventData(EventDescriptions.userHasBeenUnblocked(user.getLogin()))));
    }
}
