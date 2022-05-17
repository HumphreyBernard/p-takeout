package ivan.takeout.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import ivan.takeout.common.BaseContext;
import ivan.takeout.common.R;
import ivan.takeout.entity.ShoppingCart;
import ivan.takeout.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Maximilian_Li
 */
@RestController
@Slf4j
@RequestMapping("/shoppingCart")
public class CartController {
    @Resource
    @Lazy
    private ShoppingCartService shoppingCartService;

    /*###################################################*/

    /**
     * 添加一个
     */
    @PostMapping("/add")
    public R<ShoppingCart> addToCart(@RequestBody ShoppingCart info) {
        // 指定用户id
        Long currentUser = BaseContext.getCurrentId();
        info.setUserId(currentUser);

        // 更新时间
        info.setCreateTime(LocalDateTime.now());
        info.setUpdateTime(LocalDateTime.now());

        // 添加或修改
        ShoppingCart tar = shoppingCartService.saveOrUpdateItem(info);
        return R.success(tar);
    }

    /*###################################################*/

    /**
     * 减少一个
     */
    @PostMapping("/sub")
    public R<ShoppingCart> sub(@RequestBody ShoppingCart item) {
        // 指定用户id
        Long currentId = BaseContext.getCurrentId();
        item.setUserId(currentId);

        // 更新时间
        item.setUpdateTime(LocalDateTime.now());

        // 删除或修改
        ShoppingCart tar = shoppingCartService.removeOrUpdateItem(item);
        return R.success(tar);
    }

    /*###################################################*/

    /**
     * 查询当前登录用户的购物车信息
     */
    @GetMapping("/list")
    public R<List<ShoppingCart>> list() {
        LambdaQueryWrapper<ShoppingCart> l = new LambdaQueryWrapper<>();
        Long currentId = BaseContext.getCurrentId();
        l.eq(ShoppingCart::getUserId, currentId)
                .orderByAsc(ShoppingCart::getUpdateTime);
        List<ShoppingCart> items = shoppingCartService.list(l);
        return R.success(items);
    }

    /*###################################################*/

    /**
     * 清空购物车
     */
    @DeleteMapping("/clean")
    public R<String> clearAll() {
        Long currentId = BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> l = new LambdaQueryWrapper<>();
        l.eq(ShoppingCart::getUserId, currentId);
        shoppingCartService.remove(l);
        return R.success("购物车清空成功");
    }
}
