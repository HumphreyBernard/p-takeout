package ivan.takeout.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 菜品口味
 *
 * @author Maximilian_Li
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishFlavor implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 菜品id
     */
    private Long dishId;


    /**
     * 口味名称
     */
    private String name;


    /**
     * 口味数据list的字符串形式
     */
    private String value;


    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    @TableField(fill = FieldFill.INSERT)
    private Long createUser;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;


    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDeleted;

}
