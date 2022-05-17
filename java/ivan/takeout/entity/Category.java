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
 * 分类
 *
 * @author Maximilian_Li
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;


    /**
     * 类型 1 菜品分类 2 套餐分类
     */
    private Integer type;


    /**
     * 分类名称
     */
    private String name;


    /**
     * 顺序
     */
    private Integer sort;


    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;


    /**
     * 修改人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;


    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDeleted;

}
