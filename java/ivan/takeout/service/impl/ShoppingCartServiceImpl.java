package ivan.takeout.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ivan.takeout.entity.ShoppingCart;
import ivan.takeout.mapper.ShoppingCartMapper;
import ivan.takeout.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author Maximilian_Li
 */
@Service
@Slf4j
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
    @Override
    public ShoppingCart saveOrUpdateItem(ShoppingCart item) {
        LambdaQueryWrapper<ShoppingCart> l1 = new LambdaQueryWrapper<>();
        // 同一个用户
        l1.eq(ShoppingCart::getUserId, item.getUserId());

        if (!Objects.equals(null, item.getDishId())) {
            // 菜品：菜品id与口味相同
            l1.eq(ShoppingCart::getDishId, item.getDishId());
        } else {
            // 套餐：套餐id相同
            l1.eq(ShoppingCart::getSetmealId, item.getSetmealId());
        }
        ShoppingCart one = this.getOne(l1);

        if (Objects.equals(null, one)) {
            // 没找到：添加
            item.setNumber(1);
            this.save(item);
        } else {
            // 找到：更新
            LambdaUpdateWrapper<ShoppingCart> l2 = new LambdaUpdateWrapper<>();
            l2.set(ShoppingCart::getNumber, one.getNumber() + 1)
                    .eq(ShoppingCart::getId, one.getId());

            this.update(l2);
        }

        return this.getOne(l1);
    }

    @Override
    public ShoppingCart removeOrUpdateItem(ShoppingCart item) {
        // 找到对应记录
        LambdaQueryWrapper<ShoppingCart> l1 = new LambdaQueryWrapper<>();
        l1.eq(ShoppingCart::getUserId, item.getUserId());

        if (Objects.equals(null, item.getSetmealId())) {
            // 菜品
            l1.eq(ShoppingCart::getDishId, item.getDishId());
        } else {
            // 套餐
            l1.eq(ShoppingCart::getSetmealId, item.getSetmealId());
        }
        ShoppingCart rec = this.getOne(l1);

        log.info(String.valueOf(rec));

        // 根据记录的number判断删除/修改
        if (Objects.equals(1, rec.getNumber())) {
            // 删除
            this.removeById(rec);
        } else {
            // 修改
            LambdaUpdateWrapper<ShoppingCart> l2 = new LambdaUpdateWrapper<>();
            l2.set(ShoppingCart::getNumber, rec.getNumber() - 1)
                    .eq(ShoppingCart::getId, rec.getId());
            this.update(l2);
        }

        return this.getOne(l1);
    }
}
