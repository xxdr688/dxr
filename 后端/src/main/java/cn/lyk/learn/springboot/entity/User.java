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
 * 
 * </p>
 *
 * @author 云海
 * @since 2023-09-14
 */
@Getter
@Setter
@TableName("sys_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

      //id
      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      //用户名
      private String username;

      //密码
      private String password;

      //昵称
      private String nickname;

      //邮箱
      private String email;

      //电话
      private String phone;

      //地址
      private String address;

      //创建时间
      private LocalDateTime createTime;

      //图片路径
      private String avatarUrl;

      //角色
      private String role;


}
