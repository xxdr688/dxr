package cn.lyk.learn.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author 云海
 * @since 2023-09-25
 */
@Getter
@Setter
@TableName("warehouse")
public class Warehouse implements Serializable {

    private static final long serialVersionUID = 1L;

      //ID
      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      //名称
      private String name;

      //类型
      private String type;

      //容量
      private String number;

      //创建时间
      private LocalDateTime createTime;


}
