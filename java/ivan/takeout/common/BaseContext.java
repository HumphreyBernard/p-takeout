package ivan.takeout.common;

/**
 * 基于ThreadLocal封装的工具类，用于保存和获取当前登陆用户的id
 *
 * @author Maximilian_Li
 */
public class BaseContext {
    private static ThreadLocal<Long> th = new ThreadLocal<>();

    public static Long getCurrentId() {
        return th.get();
    }

    public static void setCurrentId(Long id) {
        th.set(id);
    }
}


