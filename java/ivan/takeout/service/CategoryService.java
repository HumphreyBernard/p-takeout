package ivan.takeout.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ivan.takeout.entity.Category;

/**
 * @author Maximilian_Li
 */
public interface CategoryService extends IService<Category> {
    /**
     * 根据id来删除分类，在删除之前先判断
     *
     * @param id 分类id
     */
    void removeAfterJudge(Long id);


    /**
     * 根据category的name判断是新增还是更新
     *
     * @param category 分类
     */
    void saveOrUpdateCategory(Category category);
}
