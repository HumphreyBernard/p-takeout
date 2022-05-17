package ivan.takeout.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ivan.takeout.common.R;
import ivan.takeout.dto.DishDto;
import ivan.takeout.entity.Dish;
import ivan.takeout.entity.DishFlavor;
import ivan.takeout.service.DishFlavorService;
import ivan.takeout.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author Maximilian_Li
 */
@RestController
@Slf4j
@RequestMapping("/dish")
public class DishController {

    @Resource
    @Lazy
    private DishService dishService;

    @Resource
    @Lazy
    private DishFlavorService dishFlavorService;

    /*###################################################*/

    /**
     * 添加菜品
     */
    @PostMapping
    public R<String> addDish(@RequestBody DishDto dishDto) {
        dishService.saveDishWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }

    /*###################################################*/

    /**
     * 分页: 单表查询+代码层组装 -> dto
     */
    @GetMapping("/page")
    public R<IPage<DishDto>> page(Integer page, Integer pageSize, String name) {

        IPage<Dish> ipDish = new Page<>(page, pageSize);
        IPage<DishDto> ipDto = dishService.getDtoPage(ipDish, name);

        return R.success(ipDto);
    }

    /*###################################################*/

    /**
     * 作为修改的前置动作
     */
    @GetMapping("/{id}")
    public R<DishDto> getById(@PathVariable("id") Long id) {
        DishDto one = dishService.getOneFromDishAndFlavor(id);
        return R.success(one);
    }

    /**
     * 修改菜品信息
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto) {
        dishService.updateOneToDishAndFlavor(dishDto);
        return R.success("更新成功");
    }

    /*###################################################*/

    /**
     * 根据条件（封装在dish内）查询对应菜品集合
     */
    @GetMapping("/list")
    public R<List<DishDto>> getDishes(Dish dish) {
        // 获取dish
        LambdaQueryWrapper<Dish> l = new LambdaQueryWrapper<>();
        l.eq(!Objects.equals(null, dish.getCategoryId()), Dish::getCategoryId, dish.getCategoryId()).
                eq(Dish::getStatus, 1).
                orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> list = dishService.list(l);

        // 创建dto并添加口味
        List<DishDto> dtoList = list.stream().map((d) -> {
            DishDto dto = new DishDto();
            LambdaQueryWrapper<DishFlavor> l2 = new LambdaQueryWrapper<>();
            l2.eq(DishFlavor::getDishId, d.getId());
            List<DishFlavor> fs = dishFlavorService.list(l2);

            BeanUtils.copyProperties(d, dto);
            dto.setFlavors(fs);
            return dto;
        }).toList();

        return R.success(dtoList);
    }

    /*###################################################*/

    /**
     * 删除菜品
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        dishService.removeAfterJudge(ids);
        return R.success("删除成功");
    }

    /*###################################################*/

    /**
     * （批量）起售、停售菜品
     */
    @PostMapping("/status/{status}")
    public R<String> status(@PathVariable("status") Integer status, @RequestParam List<Long> ids) {
        // 检查是否包含在套餐中
        dishService.judgeBeforeAlterStatus(ids);
        // 更新
        LambdaQueryWrapper<Dish> l = new LambdaQueryWrapper<>();
        l.in(!Objects.equals(null, ids), Dish::getId, ids);
        Dish d = new Dish();
        d.setStatus(status);
        dishService.update(d, l);
        return R.success("修改菜品状态成功");
    }
}
