package ivan.takeout.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import ivan.takeout.dto.SetmealDto;
import ivan.takeout.entity.Setmeal;

import java.util.List;

/**
 * @author Maximilian_Li
 */
public interface SetmealService extends IService<Setmeal> {
    /**
     * 向setmeal与setmeal dish表中添加数据
     * 1. 把基本信息保存到setmeal表中
     * 2. dto中有传过来的套餐中菜品对应关系的列表，将其保存到setmeal dish表中，需要手动置入套餐id
     */
    void saveSetmealAndDish(SetmealDto setmealDto);

    IPage<SetmealDto> getDtoPage(IPage<Setmeal> ipS, String name);

    void removeAfterJudge(List<Long> ids);

    SetmealDto getOneFromComboAndRelation(Long id);

    void updateOneToComboAndRelation(SetmealDto dto);
}
