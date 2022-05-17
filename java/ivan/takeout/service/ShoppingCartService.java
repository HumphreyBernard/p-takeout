package ivan.takeout.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ivan.takeout.entity.ShoppingCart;

/**
 * @author Maximilian_Li
 */
public interface ShoppingCartService extends IService<ShoppingCart> {
    /**
     * 更新或者新增一个购物车项
     */
    ShoppingCart saveOrUpdateItem(ShoppingCart item);

    /**
     * 删除或者新增一个购物车项
     */
    ShoppingCart removeOrUpdateItem(ShoppingCart item);
}
