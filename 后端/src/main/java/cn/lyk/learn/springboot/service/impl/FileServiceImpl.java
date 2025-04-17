package cn.lyk.learn.springboot.service.impl;

import cn.lyk.learn.springboot.entity.Files;
import cn.lyk.learn.springboot.mapper.FileMapper;
import cn.lyk.learn.springboot.service.IFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 云海
 * @since 2023-09-24
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, Files> implements IFileService {

}
