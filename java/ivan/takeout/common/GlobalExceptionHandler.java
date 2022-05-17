package ivan.takeout.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理
 *
 * @author Maximilian_Li
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException e) {
        log.error(e.getMessage());

        // employee主键username重复
        if (e.getMessage().contains("Duplicate entry")) {
            String[] s = e.getMessage().split(" ");
            String msg = "属性 <" + s[2] + "> 已存在";
            return R.error(msg);
        }

        return R.error("哎呀，出错了（^_^!）");
    }

    @ExceptionHandler(ServiceException.class)
    public R<String> exceptionHandler(ServiceException e) {
        log.error(e.getMessage());
        return R.error(e.getMessage());
    }
}
