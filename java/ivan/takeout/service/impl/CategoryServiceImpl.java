package ivan.takeout.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ivan.takeout.common.ServiceException;
import ivan.takeout.entity.Category;
import ivan.takeout.entity.Dish;
import ivan.takeout.entity.Setmeal;
import ivan.takeout.mapper.CategoryMapper;
import ivan.takeout.service.CategoryService;
import ivan.takeout.service.DishService;
import ivan.takeout.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author Maximilian_Li
 */
@Service
@Slf4j
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    @Lazy
    private DishService dishService;

    @Resource
    @Lazy
    private SetmealService setmealService;

    @Override
    public void removeAfterJudge(Long id) {
        // 查询分类是否关联菜品，如果有关联则不能删除（抛出业务异常），没有关联即可删除
        LambdaQueryWrapper<Dish> l1 = new LambdaQueryWrapper<>();
        l1.eq(!Objects.equals(null, id), Dish::getCategoryId, id);
        long c1 = dishService.count(l1);
        if (c1 > 0) {
            log.info("当前分类有关联菜品");
            throw new ServiceException("当前分类有关联菜品，不能删除");
        }


        // 查询分类是否关联套餐，如果有关联则不能删除（抛出业务异常），没有关联即可删除
        LambdaQueryWrapper<Setmeal> l2 = new LambdaQueryWrapper<>();
        l2.eq(!Objects.equals(null, id), Setmeal::getCategoryId, id);
        long c2 = setmealService.count(l2);
        if (c2 > 0) {
            log.info("当前分类有关联套餐");
            throw new ServiceException("当前分类有关联套餐，不能删除");
        }

        // 删除
        super.removeById(id);
    }

    @Override
    public void saveOrUpdateCategory(Category category) {
        /*
         * 1. 根据传入的category的name判断是否为逻辑删除的一条数据
         * 2. 如果是，则修改
         * 3. 如果不是，新增
         */
        Category tar = categoryMapper.selectInDeleted(category.getName());
        if (!Objects.equals(null, tar)) {
            categoryMapper.updateLogicDeleted(tar.getId());
        } else {
            categoryMapper.insert(category);
        }
    }
}
