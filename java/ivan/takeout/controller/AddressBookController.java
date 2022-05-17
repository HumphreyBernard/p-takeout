package ivan.takeout.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import ivan.takeout.common.BaseContext;
import ivan.takeout.common.R;
import ivan.takeout.entity.AddressBook;
import ivan.takeout.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @author Maximilian_Li
 */
@RestController
@Slf4j
@RequestMapping("/addressBook")
public class AddressBookController {

    @Resource
    @Lazy
    private AddressBookService addressBookService;

    /*########################################################*/

    /**
     * 新增地址
     */
    @PostMapping
    public R<AddressBook> save(@RequestBody AddressBook addr) {
        addr.setUserId(BaseContext.getCurrentId());
        addressBookService.save(addr);
        return R.success(addr);
    }

    /*########################################################*/

    /**
     * 得到该用户的所有地址信息
     */
    @GetMapping("/list")
    public R<List<AddressBook>> getAll() {
        LambdaQueryWrapper<AddressBook> l = new LambdaQueryWrapper<>();
        l.eq(AddressBook::getUserId, BaseContext.getCurrentId());
        l.orderByDesc(AddressBook::getUpdateTime);
        List<AddressBook> list = addressBookService.list(l);
        return R.success(list);
    }

    /*########################################################*/

    /**
     * 添加一个默认地址（默认地址的数量只能是一个）
     */
    @PutMapping("/default")
    public R<String> setDefault(@RequestBody AddressBook a) {
        // 1. 将该用户的所有地址的默认字段全部置零
        Long userId = BaseContext.getCurrentId();
        LambdaUpdateWrapper<AddressBook> l1 = new LambdaUpdateWrapper<>();
        l1.set(AddressBook::getIsDefault, 0)
                .eq(AddressBook::getUserId, userId);
        addressBookService.update(l1);

        // 2. 将该地址的默认字段置一
        a.setIsDefault(1);
        addressBookService.updateById(a);

        return R.success("默认地址设置成功");
    }

    /*########################################################*/

    /**
     * 根据id查询一条地址信息
     */
    @GetMapping("/{id}")
    public R<AddressBook> getOne(@PathVariable("id") Long id) {
        AddressBook byId = addressBookService.getById(id);
        if (Objects.equals(null, byId)) {
            return R.error("当前地址信息不存在");
        }

        return R.success(byId);
    }

    /**
     * 更新
     */
    @PutMapping
    public R<AddressBook> update(@RequestBody AddressBook address) {
        addressBookService.updateById(address);
        return R.success(address);
    }

    /*########################################################*/

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public R<String> delete(@PathVariable("id") Long id) {
        addressBookService.removeById(id);
        return R.success("删除成功");
    }

    /*########################################################*/

    /**
     * 查询默认地址
     */
    @GetMapping("/default")
    public R<AddressBook> getDefault(HttpSession session) {
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getUserId, BaseContext.getCurrentId());
        queryWrapper.eq(AddressBook::getIsDefault, 1);

        AddressBook addressBook = addressBookService.getOne(queryWrapper);

        if (null == addressBook) {
            return R.error("没有找到该对象");
        } else {
            if (!Objects.equals(null, session.getAttribute("orderTime"))) {
                session.removeAttribute("orderTime");
            }
            session.setAttribute("orderTime", LocalDateTime.now());
            return R.success(addressBook);
        }
    }
}
