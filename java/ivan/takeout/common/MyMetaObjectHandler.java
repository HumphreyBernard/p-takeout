package ivan.takeout.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 自定义的元数据对象处理器
 *
 * @author Maximilian_Li
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    /**
     * 插入时自动填充
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("公共字段自动填充【insert】");
        log.info(metaObject.toString());

        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        /*
        通过thread local在filter中设置当前登录用户的id，并在这里获取
        1. 编写BaseContext工具类，是一个基于ThreadLocal的工具类
        2. 在LoginCheckFilter的doFilter中调用BaseContext来设置当前登录用户的id
        3. 在MyMetaObjectHandler的方法中调用BaseContext获取登录用户的id
         */
        Long id = BaseContext.getCurrentId();
        metaObject.setValue("updateUser", id);
        metaObject.setValue("createUser", id);
    }

    /**
     * 修改时自动填充
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("公共字段自动填充【update】");
        log.info(metaObject.toString());

        Long id = BaseContext.getCurrentId();
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", id);
    }
}
