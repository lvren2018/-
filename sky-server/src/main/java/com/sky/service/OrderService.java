package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.vo.OrderSubmitVO;

import java.util.List;

public interface OrderService {

    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO);

}
