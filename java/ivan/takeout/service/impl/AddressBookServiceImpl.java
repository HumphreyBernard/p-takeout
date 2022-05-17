package ivan.takeout.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ivan.takeout.entity.AddressBook;
import ivan.takeout.mapper.AddressBookMapper;
import ivan.takeout.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
