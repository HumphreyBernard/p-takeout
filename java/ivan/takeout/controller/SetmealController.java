package ivan.takeout.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ivan.takeout.common.R;
import ivan.takeout.dto.SetmealDto;
import ivan.takeout.entity.Setmeal;
import ivan.takeout.entity.SetmealDish;
import ivan.takeout.service.SetmealDishService;
import ivan.takeout.service.SetmealService;
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
@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Resource
    @Lazy
    private SetmealService setmealService;

    @Resource
    @Lazy
    private SetmealDishService setmealDishService;
    /*###################################################*/

    /**
     * 添加套餐
     */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto) {
        setmealService.saveSetmealAndDish(setmealDto);
        return R.success("套餐添加成功");
    }

    /*###################################################*/

    /**
     * 模糊查询与分页查询
     */
    @GetMapping("/page")
    public R<IPage<SetmealDto>> page(Integer page, Integer pageSize, String name) {
        IPage<Setmeal> ipS = new Page<>(page, pageSize);
        IPage<SetmealDto> ipSd = setmealService.getDtoPage(ipS, name);
        return R.success(ipSd);
    }

    /*###################################################*/

    /**
     * 删除套餐
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        /*
        删除套餐时，套餐一定是禁售状态
        删除套餐时，关联的“套餐-菜品”表中对应数据也应该被删除
         */
        setmealService.removeAfterJudge(ids);
        return R.success("删除成功");
    }

    /*###################################################*/

    /**
     * （批量）起售、停售菜品
     */
    @PostMapping("/status/{status}")
    public R<String> status(@PathVariable("status") Integer status, @RequestParam List<Long> ids) {
        LambdaQueryWrapper<Setmeal> l = new LambdaQueryWrapper<>();
        l.in(!Objects.equals(null, ids), Setmeal::getId, ids);
        Setmeal c = new Setmeal();
        c.setStatus(status);
        setmealService.update(c, l);
        return R.success("修改菜品状态成功");
    }

    /*###################################################*/

    /**
     * 获取一个被修改的套餐信息
     */
    @GetMapping("/{id}")
    public R<SetmealDto> getById(@PathVariable("id") Long id) {
        SetmealDto dto = setmealService.getOneFromComboAndRelation(id);
        return R.success(dto);
    }

    /**
     * 修改上面获得的套餐信息
     */
    @PutMapping
    public R<String> updateOne(@RequestBody SetmealDto dto) {
        setmealService.updateOneToComboAndRelation(dto);
        return R.success("更新成功");
    }

    /*###################################################*/

    @GetMapping("/list")
    public R<List<SetmealDto>> getCombos(Setmeal combo) {
        LambdaQueryWrapper<Setmeal> l1 = new LambdaQueryWrapper<>();
        l1.eq(!Objects.equals(null, combo.getCategoryId()), Setmeal::getCategoryId, combo.getCategoryId())
                .eq(Setmeal::getStatus, 1)
                .orderByDesc(Setmeal::getUpdateTime);
        List<Setmeal> combos = setmealService.list(l1);

        List<SetmealDto> dtos = combos.stream().map((c) -> {
            SetmealDto dto = new SetmealDto();
            LambdaQueryWrapper<SetmealDish> l2 = new LambdaQueryWrapper<>();
            l2.eq(SetmealDish::getSetmealId, c.getId());
            List<SetmealDish> sd = setmealDishService.list(l2);

            BeanUtils.copyProperties(c, dto);
            dto.setSetmealDishes(sd);
            return dto;
        }).toList();

        return R.success(dtos);
    }
}
