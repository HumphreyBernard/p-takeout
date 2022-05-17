package ivan.takeout.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ivan.takeout.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Maximilian_Li
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
    /**
     * 根据要添加的分类的名称在数据库中查询是否存在被逻辑删除的记录
     *
     * @param cName 分类名称
     * @return 分类名称的对象
     */
    Category selectInDeleted(@Param("cat") String cName);

    /**
     * 根据被逻辑删除的分类的id更新isDeleted，使之恢复
     *
     * @param id id
     */
    void updateLogicDeleted(@Param("identifier") Long id);
}
