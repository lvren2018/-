package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import io.swagger.models.auth.In;

import java.util.List;

public interface DishService {

    /**
     * 更新菜品启售状态
     * @param status
     * @param id
     */
    public void updateStatus(Integer status,Long id);

    /**
     * 新增菜品以及口味
     * @param dishDTO
     */
    public void saveWithFlavor(DishDTO dishDTO);

    /**
     * 分页查询
     * @param dishPageQueryDTO
     * @return
     */
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 批量删除菜品
     * @param ids
     */
    void deleteBatch(List<Long> ids);
}
