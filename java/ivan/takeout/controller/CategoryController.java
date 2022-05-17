package ivan.takeout.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ivan.takeout.common.R;
import ivan.takeout.entity.Category;
import ivan.takeout.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author Maximilian_Li
 */
@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Resource
    @Lazy
    private CategoryService categoryService;

    /*###################################################*/

    /**
     * 新增一个分类
     */
    @PostMapping
    public R<String> save(@RequestBody Category category) {
        log.info("category: {}", category);
        categoryService.saveOrUpdateCategory(category);
        return R.success("新增分类成功");
    }

    /*###################################################*/

    /**
     * 分页查询
     */
    @GetMapping("/page")
    public R<IPage<Category>> page(Integer page, Integer pageSize) {
        IPage<Category> cp = new Page<>(page, pageSize);

        LambdaQueryWrapper<Category> l = new LambdaQueryWrapper<>();
        l.orderByAsc(Category::getSort);

        categoryService.page(cp, l);

        return R.success(cp);
    }

    /*###################################################*/

    /**
     * 删除分类
     */
    @DeleteMapping
    public R<String> delete(Long id) {
        /*
        如果该菜品分类下有具体的菜/套餐，应该不能删除
         */
        categoryService.removeAfterJudge(id);

        return R.success("删除成功");
    }

    /*###################################################*/

    /**
     * 修改分类
     */
    @PutMapping
    public R<String> update(@RequestBody Category category) {
        categoryService.updateById(category);
        return R.success("修改成功");
    }

    /*###################################################*/

    /**
     * 添加菜品前先要获取菜品分类集合
     */
    @GetMapping("/list")
    public R<List<Category>> getList(Category category) {
        if (category.getType() == null) {
            log.info("客户端调用");
        } else {
            log.info("后台管理端调用");

        }

        // 条件
        LambdaQueryWrapper<Category> l = new LambdaQueryWrapper<>();
        l.eq(!Objects.equals(null, category.getType()), Category::getType, category.getType());
        l.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        // 查询
        List<Category> list = categoryService.list(l);

        return R.success(list);
    }
}
