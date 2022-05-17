package ivan.takeout.dto;


import ivan.takeout.entity.Setmeal;
import ivan.takeout.entity.SetmealDish;
import lombok.Data;

import java.util.List;

/**
 * @author Maximilian_Li
 */
@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
