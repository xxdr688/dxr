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
 * @since 2023-09-24
 */
@Getter
@Setter
@TableName("sys_file")
public class Files implements Serializable {

    private static final long serialVersionUID = 1L;

      //id
      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      //文件名称
      private String name;

      //文件类型
      private String type;

      //文件大小(kb)
      private Long size;

      //下载链接
      private String url;

      //文件md5
      private String md5;

      //是否删除
      private Boolean isDelete;

      //是否禁用链接
      private Boolean enable;


}
