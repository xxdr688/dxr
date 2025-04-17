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
@TableName("wzsl")
public class Wzsl implements Serializable {

    private static final long serialVersionUID = 1L;

      //ID
      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      //申领人
      private String name;

      //物资名称
      private String wzname;

      //申领数量
      private String wznumber;

      //申领时间
      private LocalDateTime wztime;

      //是否归还
      private String flag;

      //是否入库
      private Integer flagnumber;

      private String rfid;


}
