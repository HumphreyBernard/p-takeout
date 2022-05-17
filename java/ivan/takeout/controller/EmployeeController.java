package ivan.takeout.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ivan.takeout.common.R;
import ivan.takeout.entity.Employee;
import ivan.takeout.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author Maximilian_Li
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Resource
    @Lazy
    private EmployeeService employeeService;

    /*###################################################*/

    /**
     * 员工登录
     */
    @PostMapping("/login")
    public R<Employee> login(@RequestBody Employee employee, HttpServletRequest request) {
        /*
        1. 将页面提交的密码进行md5加密处理
        2. 根据用户名向数据库进行查询，没有找到就返回登陆失败
        3. 比对密码，不一致就返回登陆失败结果
        4. 查看员工状态，如果处于禁用状态返回员工已禁用结果
        5. 登陆成功，将员工id存入session并返回登陆成功结果
         */
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));

        LambdaQueryWrapper<Employee> l = new LambdaQueryWrapper<>();
        l.eq(Employee::getUsername, employee.getUsername());
        Employee one = employeeService.getOne(l);

        if (one == null) {
            return R.error("用户名错误，登陆失败");
        }

        if (!Objects.equals(one.getPassword(), password)) {
            return R.error("密码错误，登陆失败");
        }

        if (one.getStatus() == 0) {
            return R.error("您已被系统禁用，登录失败");
        }

        request.getSession().setAttribute("employee", one.getId());
        return R.success(one);
    }

    /*###################################################*/

    /**
     * 员工退出
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        /*
        1. 清除session中的employee信息
         */
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    /*###################################################*/

    /**
     * 保存新的员工信息
     */
    @PostMapping
    public R<String> save(@RequestBody Employee employee) {
        /*
        1. 设置初始密码为123456的md5加密后的密码、填充其他的无默认的属性
        2. 调用service的save方法向数据库中存储数据
        3. 如果添加的时候存在异常，则异常处理器（GlobalExceptionHandler）可以进行全局捕获
         */

        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        // 公共字段自动填充

        employeeService.save(employee);

        return R.success("用户添加成功");
    }

    /*###################################################*/

    /**
     * 员工信息的分页查询
     */
    @GetMapping("/page")
    public R<IPage<Employee>> page(Employee employee, Integer page, Integer pageSize) {
        /*
        1. 构造分页构造器IPage
        2. 构造条件查询构造器
        3. 执行查询
         */
        IPage<Employee> pageInfo = new Page<>(page, pageSize);

        LambdaQueryWrapper<Employee> l = new LambdaQueryWrapper<>();
        l.like(StringUtils.isNotEmpty(employee.getName()), Employee::getName, employee.getName());
        l.orderByDesc(Employee::getUpdateTime);

        employeeService.page(pageInfo, l);

        return R.success(pageInfo);
    }

    /*###################################################*/

    /**
     * 根据id查询员工信息，作为修改员工信息的前置动作
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable("id") Long id) {
        Employee byId = employeeService.getById(id);
        if (!Objects.equals(null, byId)) {
            return R.success(byId);
        } else {
            return R.error("没有查询到对应员工信息");
        }
    }

    /**
     * 通用的修改方法：ban与update
     */
    @PutMapping
    public R<String> update(@RequestBody Employee employee) {
        /*
        js只能处理16位数字，但long有19位，会造成精度丢失
        处理方法一：将long转为string字符串  ✅ -> 通过类JacksonObjectMapper进行转换并序列化，在WebMvcConfig配置类中扩展
        处理方法二：将表中bigint改为int     ❌
        ->作为饭店的外卖平台，员工人数超过int的范围不太现实，因此将员工的id类型修改为int，但是订单、订单详情的数目很可能超过int的范围
        ->如果要使用id类型为int且设置为自增的话，订单就不能使用雪花算法获得唯一值
         */
        // 公共字段自动填充

        employeeService.updateById(employee);
        return R.success("员工信息修改成功");
    }
}
