package ivan.takeout.dto;


import ivan.takeout.entity.Dish;
import ivan.takeout.entity.DishFlavor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author malimilian
 */
@Data
public class DishDto extends Dish {

    /**
     * 1(Dish) : n(DishFlavor)
     */
    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
