package ivan.takeout.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ivan.takeout.common.ServiceException;
import ivan.takeout.dto.DishDto;
import ivan.takeout.entity.Category;
import ivan.takeout.entity.Dish;
import ivan.takeout.entity.DishFlavor;
import ivan.takeout.entity.SetmealDish;
import ivan.takeout.mapper.DishMapper;
import ivan.takeout.service.CategoryService;
import ivan.takeout.service.DishFlavorService;
import ivan.takeout.service.DishService;
import ivan.takeout.service.SetmealDishService;
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
import java.util.stream.Collectors;

/**
 * @author Maximilian_Li
 */
@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Resource
    @Lazy
    private DishFlavorService dishFlavorService;

    @Resource
    @Lazy
    private CategoryService categoryService;

    @Resource
    @Lazy
    private SetmealDishService setmealDishService;


    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    @Override
    public void saveDishWithFlavor(DishDto dishDto) {

        // 1. 保存基本信息到菜品表
        this.save(dishDto);

        // 2. 封装flavor中的dishId
        List<DishFlavor> flavors = dishDto.getFlavors();
        Long dishId = dishDto.getId();

        // 这么做的原因：新添加的口味没有菜品id
        flavors = flavors.stream().map((flavor) -> {
            flavor.setDishId(dishId);
            return flavor;
        }).collect(Collectors.toList());

        // 3. 保存口味信息到口味表
        dishFlavorService.saveBatch(flavors);
    }

    @Override
    public IPage<DishDto> getDtoPage(IPage<Dish> ipDish, String name) {

        /*
         * 1. 构造分页构造器
         * 2. 构造条件构造器
         * 3. 对象拷贝
         * 4. 处理
         */

        long page = ipDish.getCurrent();
        long size = ipDish.getSize();
        IPage<DishDto> ipDto = new Page<>(page, size);

        // dish中查询
        LambdaQueryWrapper<Dish> l = new LambdaQueryWrapper<>();
        l.like(!Objects.equals(name, null), Dish::getName, name).orderByAsc(Dish::getSort);
        this.page(ipDish, l);

        // 将dish泛型的页面数据拷贝到dish dto泛型的页面数据上，忽略records，因为需要将List<Dish>转换为List<DishDto>
        BeanUtils.copyProperties(ipDish, ipDto, "records");

        // 得到泛型为Dish的recO
        // 处理: 将recN设置为ipTo的records
        List<DishDto> dtos = ipDish.getRecords().stream().map((dish) -> {
            // 得到该菜品的分类id
            long cid = dish.getCategoryId();
            // 根据这个分类id查询到分类名称
            Category cat = categoryService.getById(cid);
            String cname = cat.getName();
            // 得到Dto
            DishDto dto = new DishDto();
            dto.setCategoryName(cname);
            BeanUtils.copyProperties(dish, dto);
            return dto;
        }).toList();

        ipDto.setRecords(dtos);

        return ipDto;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    @Override
    public DishDto getOneFromDishAndFlavor(Long id) {

        DishDto dto = new DishDto();

        // 根据id获取dish基本信息
        Dish dishInfo = this.getById(id);

        // 根据id获取口味
        LambdaQueryWrapper<DishFlavor> l = new LambdaQueryWrapper<>();
        l.eq(!Objects.equals(null, id), DishFlavor::getDishId, id);
        List<DishFlavor> flavors = dishFlavorService.list(l);

        BeanUtils.copyProperties(dishInfo, dto);
        dto.setFlavors(flavors);
        return dto;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void updateOneToDishAndFlavor(DishDto dishDto) {
        // 使用dto中dish的数据更新dish表
        this.updateById(dishDto);

        // 使用dto中dish flavor的flavor集合更新dish flavor表：(根据dishId)清理旧的，添加新的
        LambdaQueryWrapper<DishFlavor> l = new LambdaQueryWrapper<>();
        l.eq(!Objects.equals(null, dishDto), DishFlavor::getDishId, dishDto.getId());
        dishFlavorService.remove(l);

        List<DishFlavor> flavors = dishDto.getFlavors();
        // 这么做的原因：新添加的flavor不会显示对应菜品的id；与别的菜品重复的口味的菜品id可能是别的菜的菜品id
        flavors = flavors.stream().map((flavor) -> {
            flavor.setDishId(dishDto.getId());
            return flavor;
        }).toList();

        dishFlavorService.saveBatch(flavors);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    @Override
    public void removeAfterJudge(List<Long> ids) {
        // 检查1：被删除的菜品不能处于起售状态
        LambdaQueryWrapper<Dish> l1 = new LambdaQueryWrapper<>();
        l1.in(!Objects.equals(null, ids), Dish::getId, ids).eq(Dish::getStatus, 1);
        long count = this.count(l1);
        if (count != 0) {
            throw new ServiceException("要删除的菜品集合中存在未停售菜品，请检查后再进行删除");
        }

        // 删除
        LambdaQueryWrapper<DishFlavor> l3 = new LambdaQueryWrapper<>();
        l3.in(DishFlavor::getDishId, ids);
        dishFlavorService.remove(l3);
        this.removeByIds(ids);
    }

    @Override
    public void judgeBeforeAlterStatus(List<Long> ids) {
        LambdaQueryWrapper<SetmealDish> l1 = new LambdaQueryWrapper<>();
        l1.in(!Objects.equals(null, ids), SetmealDish::getDishId, ids);
        long count = setmealDishService.count(l1);
        List<String> names = setmealDishService.list(l1).stream().map(SetmealDish::getName).toList();

        if (count != 0) {
            throw new ServiceException("要停售的菜品集合中存在包含于套餐的菜品 " + names + " ，请检查后再进行删除");
        }
    }
}