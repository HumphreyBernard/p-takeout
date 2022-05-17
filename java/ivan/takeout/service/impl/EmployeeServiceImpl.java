package ivan.takeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ivan.takeout.entity.Employee;
import ivan.takeout.mapper.EmployeeMapper;
import ivan.takeout.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * @author Maximilian_Li
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
