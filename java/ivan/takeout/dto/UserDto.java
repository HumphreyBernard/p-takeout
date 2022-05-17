package ivan.takeout.dto;

import ivan.takeout.entity.User;
import lombok.Data;

/**
 * @author Maximilian_Li
 */
@Data
public class UserDto extends User {

    private String code;
}
