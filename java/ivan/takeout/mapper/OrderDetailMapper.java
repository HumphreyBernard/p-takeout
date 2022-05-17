package ivan.takeout.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ivan.takeout.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Maximilian_Li
 */
@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {
}
