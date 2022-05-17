package ivan.takeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ivan.takeout.entity.OrderDetail;
import ivan.takeout.mapper.OrderDetailMapper;
import ivan.takeout.service.OrderDetailService;
import org.springframework.stereotype.Service;

/**
 * @author Maximilian_Li
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
