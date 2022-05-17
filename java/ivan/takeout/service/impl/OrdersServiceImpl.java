package ivan.takeout.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ivan.takeout.common.ServiceException;
import ivan.takeout.entity.*;
import ivan.takeout.mapper.OrdersMapper;
import ivan.takeout.service.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Maximilian_Li
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

    @Resource
    @Lazy
    private AddressBookService addressBookService;

    @Resource
    @Lazy
    private ShoppingCartService shoppingCartService;

    @Resource
    @Lazy
    private OrderDetailService orderDetailService;

    @Resource
    @Lazy
    private UserService userService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void submitOrder(Orders o) {
        // 购物车
        LambdaQueryWrapper<ShoppingCart> l1 = new LambdaQueryWrapper<>();
        l1.eq(ShoppingCart::getUserId, o.getUserId());
        List<ShoppingCart> cart = shoppingCartService.list(l1);
        if (Objects.equals(null, cart) || cart.size() == 0) {
            throw new ServiceException("购物车为空");
        }

        // 用户
        LambdaQueryWrapper<User> l2 = new LambdaQueryWrapper<>();
        l2.eq(User::getId, o.getUserId());
        User user = userService.getOne(l2);
        if (Objects.equals(null, user)) {
            throw new ServiceException("用户不存在");
        }

        // 地址
        LambdaQueryWrapper<AddressBook> l3 = new LambdaQueryWrapper<>();
        l3.eq(AddressBook::getId, o.getAddressBookId());
        AddressBook addr = addressBookService.getOne(l3);
        if (Objects.equals(null, addr)) {
            throw new ServiceException("地址不存在");
        }

        // 组装 order 与 order detail
        String phone = user.getPhone();
        String username = user.getName();
        String address = addr.getDetail();
        String consignee = addr.getConsignee();
        Long orderId = IdWorker.getId();
        AtomicInteger total = new AtomicInteger(0);

        List<OrderDetail> details = cart.stream().map((item) -> {
            OrderDetail detail = new OrderDetail();

            detail.setAmount(item.getAmount());
            detail.setNumber(item.getNumber());
            if (Objects.equals(null, item.getSetmealId())) {
                detail.setDishFlavor(item.getDishFlavor());
                detail.setDishId(item.getDishId());
            } else {
                detail.setSetmealId(item.getSetmealId());
            }
            detail.setName(item.getName());
            detail.setImage(item.getImage());
            detail.setOrderId(orderId);

            // 计算总价
            Integer number = item.getNumber();
            BigDecimal amount = item.getAmount();
            BigDecimal cross = amount.multiply(BigDecimal.valueOf(number));
            total.addAndGet(cross.intValue());

            return detail;
        }).toList();

        o.setUserName(username);
        o.setAmount(new BigDecimal(total.get()));
        o.setStatus(2);
        o.setPhone(phone);
        o.setConsignee(consignee);
        o.setAddress(address);
        o.setCheckoutTime(LocalDateTime.now());
        o.setNumber(String.valueOf(orderId));

        // 向订单表中插入一条数据
        this.save(o);

        // 向订单明细表中插入至少一条数据
        orderDetailService.saveBatch(details);

        // 清空当前用户的购物车数据
        shoppingCartService.remove(l1);
    }
}
