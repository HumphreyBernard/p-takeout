package ivan.takeout.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import ivan.takeout.common.R;
import ivan.takeout.common.SmsManager;
import ivan.takeout.common.ValidateCodeInitializer;
import ivan.takeout.dto.UserDto;
import ivan.takeout.entity.User;
import ivan.takeout.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Maximilian_Li
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Resource
    @Lazy
    private UserService userService;

    /*###################################################*/

    /**
     * 根据手机号发送手机短信验证码
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) {
        // 1. 获取手机号
        String phone = user.getPhone();
        String reg = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(phone);
        if (!matcher.matches()) {
            return R.error("手机号校验失败");
        }

        // 2. 生成随机验证码
        String validator = ValidateCodeInitializer.generateValidateCode4String(4);
        log.info("validation code is: " + validator);

        // 3. 调用api发送短信
        try {
            SmsManager.sendMessage(validator, phone);
        } catch (Exception e) {
            return R.error("短信发送失败");
        }

        // 4. 保存到session，形式为“手机号-验证码”
        session.setAttribute(phone, validator);

        // 5. 校验验证码
        return R.success("短信已发送");
    }

    /*###################################################*/

    /**
     * 根据手机号发送手机短信验证码
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody UserDto userDto, HttpSession session) {
        // 1. 获取手机号
        String phone = userDto.getPhone();
        // 2. 获取验证码
        String validator = userDto.getCode();
        // 3. 获取session中的验证码并比对，错误返回
        String codeInSession = (String) session.getAttribute(phone);

        // 比对失败
        if (StringUtils.isBlank(codeInSession) || !Objects.equals(validator, codeInSession)) {
            return R.error("登陆失败");
        }

        // 4. 查数据库，存在登录，不存在添加后(自动注册)登录
        LambdaQueryWrapper<User> l = new LambdaQueryWrapper<>();
        l.eq(!Objects.equals(null, phone), User::getPhone, phone);
        User user = userService.getOne(l);

        if (Objects.equals(null, user)) {
            user = new User();
            user.setPhone(phone);
            userService.save(user);
        }

        System.out.println(user.getId());
        session.setAttribute("user", user.getId());

        return R.success(user);
    }

    /*###################################################*/
}
