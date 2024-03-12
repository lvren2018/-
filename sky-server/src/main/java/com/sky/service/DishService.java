package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;
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

    /**
     * 根据id查询菜品数据
     * @param id
     * @return
     */
    DishVO getById(Long id);

    /**
     * 修改菜品数据
     * @param dishDTO
     */
    void update(DishDTO dishDTO);

    /**
     * 条件查询菜品和口味
     * @param dish
     * @return
     */
    List<DishVO> listWithFlavor(Dish dish);

    /**
     * 根据分类查询菜品
     * @param categoryId
     * @return
     */
    List<Dish> list(Long categoryId);
}
