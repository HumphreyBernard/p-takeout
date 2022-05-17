package ivan.takeout.controller;

import ivan.takeout.common.BaseContext;
import ivan.takeout.common.R;
import ivan.takeout.entity.Orders;
import ivan.takeout.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

/**
 * @author Maximilian_Li
 */
//@RequestMapping("/order")
@Slf4j
@RestController
public class OrderController {

    @Resource
    @Lazy
    private OrdersService ordersService;

    /*###################################################*/

    /**
     * 下单
     */
    @PostMapping("/order/submit")
    public R<String> submit(@RequestBody Orders order, HttpSession session) {
        // 传入的参数有remark、addressBookId、payMethod
        log.info(String.valueOf(order));
        // 置入当前用户id
        Long currentId = BaseContext.getCurrentId();
        order.setUserId(currentId);
        // 置入下单时间
        LocalDateTime orderTime = (LocalDateTime) session.getAttribute("orderTime");
        order.setOrderTime(orderTime);

        ordersService.submitOrder(order);
        return R.success("下单成功");
    }

    /*###################################################*/
    /*###################################################*/
    /*###################################################*/
    /*###################################################*/

}
