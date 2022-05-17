package ivan.takeout.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String name;

    private String password;

    private String phone;

    private String sex;

    private String idNumber;

    /**
     * 1 正常使用，0 被锁定
     */
    private Integer status;

    /**
     * 创建时间——插入时填充字段
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改时间——插入和更新时更新字段
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 创建者——插入时填充字段
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    /**
     * 修改者——插入和更新时更新字段
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

}
