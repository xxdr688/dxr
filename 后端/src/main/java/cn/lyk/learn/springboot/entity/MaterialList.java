package cn.lyk.learn.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 物资表
 * </p>
 *
 * @author 云海
 * @since 2023-09-24
 */
@Getter
@Setter
@TableName("material_list")
public class MaterialList implements Serializable {

    private static final long serialVersionUID = 1L;


      //物资ID
      @TableId(value = "material_id", type = IdType.AUTO)
      private Integer materialId;

      //物资编号
      private String materialBh;

      //物资名称
      private String materialName;

      //物资类型
      private String materialLx;

      //物资数量
      private String materialSl;

      //物资库存数量
      private String materialKc;

      //物资已申请数量
      private String materialSq;

      //存放仓库
      private String materialWarehouse;

      //物资创建时间
      private LocalDateTime materialTime;


}
