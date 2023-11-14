package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    /**
     * 批量插入口味数据
     */
    void insertBatch(List<DishFlavor> flavor);

    /**
     * 根据菜品id删除口味id
     * @param dishId
     */
    @Select("delete from dish_flavor where dish_id = #{dishId}")
    void deleteByDishId(Long dishId);
}
