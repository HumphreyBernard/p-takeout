package ivan.takeout.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ivan.takeout.common.ServiceException;
import ivan.takeout.dto.SetmealDto;
import ivan.takeout.entity.Category;
import ivan.takeout.entity.Setmeal;
import ivan.takeout.entity.SetmealDish;
import ivan.takeout.mapper.SetmealMapper;
import ivan.takeout.service.CategoryService;
import ivan.takeout.service.SetmealDishService;
import ivan.takeout.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author Maximilian_Li
 */
@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Resource
    @Lazy
    private SetmealDishService setmealDishService;

    @Resource
    @Lazy
    private CategoryService categoryService;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    @Override
    public void saveSetmealAndDish(SetmealDto setmealDto) {
        this.save(setmealDto);

        List<SetmealDish> relations = setmealDto.getSetmealDishes();
        Long sid = setmealDto.getId();

        relations = relations.stream().map((r) -> {
            r.setSetmealId(sid);
            return r;
        }).toList();

        setmealDishService.saveBatch(relations);
    }

    @Override
    public IPage<SetmealDto> getDtoPage(IPage<Setmeal> ipS, String name) {
        long page = ipS.getCurrent();
        long size = ipS.getSize();

        // 套餐
        LambdaQueryWrapper<Setmeal> l = new LambdaQueryWrapper<>();
        l.like(!Objects.equals(null, name), Setmeal::getName, name).orderByDesc(Setmeal::getUpdateTime);
        this.page(ipS, l);

        // 拷贝时忽略records，因为泛型不一样
        IPage<SetmealDto> ipSd = new Page<>(page, size);
        BeanUtils.copyProperties(ipS, ipSd, "records");

        // 套餐dto
        List<SetmealDto> records = ipS.getRecords().stream().map((rec) -> {
            Long categoryId = rec.getCategoryId();
            Category byId = categoryService.getById(categoryId);
            String categoryName = byId.getName();

            SetmealDto dto = new SetmealDto();
            dto.setCategoryName(categoryName);
            BeanUtils.copyProperties(rec, dto);
            return dto;
        }).toList();
        ipSd.setRecords(records);

        return ipSd;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    @Override
    public void removeAfterJudge(List<Long> ids) {
        /*
        1. 判断status是否为0
        2. 删除关系表中对应数据、删除套餐表中对应数据
        3. 删除服务器中对应图片
        */

        // 在将要删除的套餐的集合中查询可以售卖的套餐数目，数目大于0就抛出异常
        LambdaQueryWrapper<Setmeal> l1 = new LambdaQueryWrapper<>();
        l1.in(!Objects.equals(null, ids), Setmeal::getId, ids).eq(Setmeal::getStatus, 1);
        long count = this.count(l1);
        if (count > 0) {
            throw new ServiceException("要删除的套餐集合中存在未停售套餐，请检查后再进行删除");
        }

        // 删除关系表中对应数据、删除套餐表中对应数据
        LambdaQueryWrapper<SetmealDish> l2 = new LambdaQueryWrapper<>();
        l2.in(SetmealDish::getSetmealId, ids);
        setmealDishService.remove(l2);
        this.removeByIds(ids);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    @Override
    public SetmealDto getOneFromComboAndRelation(Long id) {
        // 1. 获取套餐基本信息
        Setmeal combo = this.getById(id);

        // 2. 获取套餐所含菜品
        LambdaQueryWrapper<SetmealDish> l = new LambdaQueryWrapper<>();
        l.eq(!Objects.equals(null, id), SetmealDish::getSetmealId, id);
        List<SetmealDish> dishes = setmealDishService.list(l);

        // 3. 整合为dto并返回
        SetmealDto dto = new SetmealDto();
        BeanUtils.copyProperties(combo, dto);
        dto.setSetmealDishes(dishes);

        return dto;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void updateOneToComboAndRelation(SetmealDto dto) {
        // 1. 更新套餐表
        this.updateById(dto);

        // 2. 删除关系
        LambdaQueryWrapper<SetmealDish> l = new LambdaQueryWrapper<>();
        l.eq(!Objects.equals(null, dto), SetmealDish::getSetmealId, dto.getId());
        setmealDishService.remove(l);

        // 3. 在赋值菜品id后新增关系
        List<SetmealDish> dishes = dto.getSetmealDishes();
        dishes = dishes.stream().map((dish) -> {
            dish.setSetmealId(dto.getId());
            return dish;
        }).toList();

        setmealDishService.saveBatch(dishes);
    }
}
