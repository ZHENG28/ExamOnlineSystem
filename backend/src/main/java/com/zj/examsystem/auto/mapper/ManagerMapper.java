package com.zj.examsystem.auto.mapper;

import com.zj.examsystem.auto.entity.Manager;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
/**
 * <p>
 *  Mapper 接口
 * </p>
 * mybatis-plus
 *
 * @author ${author}
 * @since 2022-03-27
 */
public interface ManagerMapper extends BaseMapper<Manager> {

    List<Manager> findByAccount(String account);
}
