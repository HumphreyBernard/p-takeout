package ivan.takeout.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ivan.takeout.entity.Orders;

/**
 * @author Maximilian_Li
 */
public interface OrdersService extends IService<Orders> {

    /**
     * 处理下单逻辑
     */
    void submitOrder(Orders orders);
}
