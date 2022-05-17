package ivan.takeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ivan.takeout.entity.DishFlavor;
import ivan.takeout.mapper.DishFlavorMapper;
import ivan.takeout.service.DishFlavorService;
import org.springframework.stereotype.Service;

/**
 * @author Maximilian_Li
 */
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
