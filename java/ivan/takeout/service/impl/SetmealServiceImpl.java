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

        // ??????
        LambdaQueryWrapper<Setmeal> l = new LambdaQueryWrapper<>();
        l.like(!Objects.equals(null, name), Setmeal::getName, name).orderByDesc(Setmeal::getUpdateTime);
        this.page(ipS, l);

        // ???????????????records????????????????????????
        IPage<SetmealDto> ipSd = new Page<>(page, size);
        BeanUtils.copyProperties(ipS, ipSd, "records");

        // ??????dto
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
        1. ??????status?????????0
        2. ???????????????????????????????????????????????????????????????
        3. ??????????????????????????????
        */

        // ????????????????????????????????????????????????????????????????????????????????????0???????????????
        LambdaQueryWrapper<Setmeal> l1 = new LambdaQueryWrapper<>();
        l1.in(!Objects.equals(null, ids), Setmeal::getId, ids).eq(Setmeal::getStatus, 1);
        long count = this.count(l1);
        if (count > 0) {
            throw new ServiceException("??????????????????????????????????????????????????????????????????????????????");
        }

        // ???????????????????????????????????????????????????????????????
        LambdaQueryWrapper<SetmealDish> l2 = new LambdaQueryWrapper<>();
        l2.in(SetmealDish::getSetmealId, ids);
        setmealDishService.remove(l2);
        this.removeByIds(ids);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    @Override
    public SetmealDto getOneFromComboAndRelation(Long id) {
        // 1. ????????????????????????
        Setmeal combo = this.getById(id);

        // 2. ????????????????????????
        LambdaQueryWrapper<SetmealDish> l = new LambdaQueryWrapper<>();
        l.eq(!Objects.equals(null, id), SetmealDish::getSetmealId, id);
        List<SetmealDish> dishes = setmealDishService.list(l);

        // 3. ?????????dto?????????
        SetmealDto dto = new SetmealDto();
        BeanUtils.copyProperties(combo, dto);
        dto.setSetmealDishes(dishes);

        return dto;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void updateOneToComboAndRelation(SetmealDto dto) {
        // 1. ???????????????
        this.updateById(dto);

        // 2. ????????????
        LambdaQueryWrapper<SetmealDish> l = new LambdaQueryWrapper<>();
        l.eq(!Objects.equals(null, dto), SetmealDish::getSetmealId, dto.getId());
        setmealDishService.remove(l);

        // 3. ???????????????id???????????????
        List<SetmealDish> dishes = dto.getSetmealDishes();
        dishes = dishes.stream().map((dish) -> {
            dish.setSetmealId(dto.getId());
            return dish;
        }).toList();

        setmealDishService.saveBatch(dishes);
    }
}
