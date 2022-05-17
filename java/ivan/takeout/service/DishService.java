package ivan.takeout.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import ivan.takeout.dto.DishDto;
import ivan.takeout.entity.Dish;

import java.util.List;

/**
 * @author Maximilian_Li
 */
public interface DishService extends IService<Dish> {

    /**
     * 在向dish表中插入数据的同时，向dish flavor中插入数据
     * <p>
     * 涉及多个表，添加事务控制
     */
    void saveDishWithFlavor(DishDto dishDto);

    /**
     * 分页<br>
     * 页面上不需要category id，需要的是category name——通过dish的子类：dish dto类。<br>
     * 获得category name的方法：<br>
     * 1. 多表查询：IPage-Dish -> List-Dish -> Dish -> id -> 多表查询填充一个DishDto -> List-DishDto -> IPage-DishDto<br>
     * 2. 单表查询+代码层组装：IPage-Dish -> List-Dish -> Dish -> categoryId -> 通过categoryService查询到对应name -> DishDto -> List-DishDto -> IPage-DishDto<br>
     */
    IPage<DishDto> getDtoPage(IPage<Dish> ipDish, String name);


    /**
     * 从dish与dish service两个表中获取信息，组合成dish dto对象并返回
     */
    DishDto getOneFromDishAndFlavor(Long id);

    /**
     * 向dish与dish flavor更新数据
     */
    void updateOneToDishAndFlavor(DishDto dishDto);

    /**
     * 在删除菜品前应该首先判断：菜品处于停售状态
     */
    void removeAfterJudge(List<Long> ids);

    void judgeBeforeAlterStatus(List<Long> ids);
}
