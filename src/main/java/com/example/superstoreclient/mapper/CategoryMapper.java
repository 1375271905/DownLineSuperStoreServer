package com.example.superstoreclient.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.superstoreclient.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
    @Select("""
            SELECT c3.category_id, c3.category_name
            FROM category c1
                     JOIN category c2 ON c1.category_id = c2.pid
                     JOIN category c3 ON c2.category_id = c3.pid
            WHERE c1.pid IS NULL;""")
    List<Category> getAllThirdLevelCategory();

    @Select("""
            SELECT c3.category_id, c3.category_name
                                        FROM category c1
                                                 JOIN category c2 ON c1.category_id = c2.pid
                                                 JOIN category c3 ON c2.category_id = c3.pid
                                        WHERE c1.category_id = ${id} OR c2.category_id = ${id} OR c3.category_id = ${id}; -- 获取最终的第三级分类
            """)
    List<Long> getThirdLevelCategory(Integer id);
}
