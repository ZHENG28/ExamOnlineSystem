package com.zj.examsystem.auto.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zj.examsystem.auto.entity.Testhistory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since 2022-03-27
 */
public interface TesthistoryMapper extends BaseMapper<Testhistory> {
    public Integer findByTestAndStudent(@Param("stuAccount") String stuAccount, @Param("testId") Integer testId);

    public IPage<Testhistory> selectPageWithTest(Page<Map<String, Object>> page, QueryWrapper<Map<String, Object>> queryWrapper);
}
