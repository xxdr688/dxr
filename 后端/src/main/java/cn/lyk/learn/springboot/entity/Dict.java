package cn.lyk.learn.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author 云海
 * @since 2024-04-08
 */
@Getter
@Setter
@TableName("sys_dict")
public class Dict implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
      //名称
      private String name;

      //内容
      private String value;

      //类型
      private String type;


}
