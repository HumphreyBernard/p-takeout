package ivan.takeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ivan.takeout.entity.User;
import ivan.takeout.mapper.UserMapper;
import ivan.takeout.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Maximilian_Li
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
