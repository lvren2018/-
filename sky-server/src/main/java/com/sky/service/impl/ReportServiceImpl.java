package com.sky.service.impl;

import com.sky.dto.GoodsSalesDTO;
import com.sky.entity.OrderDetail;
import com.sky.mapper.OrderDetailMapper;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 统计指定时间内的营业额
     * @param begin
     * @param end
     * @return
     */
    public TurnoverReportVO getTurnoverStatistics(LocalDate begin, LocalDate end) {
        //存放从begin到end的所有日期
        List<LocalDate> dateList = new ArrayList<>();
        //存放从begin到end的所有营业额
        List<Double> turnoverList = new ArrayList<>();
        for (LocalDate date = begin; !date.isAfter(end); date = date.plusDays(1)) {
            dateList.add(date);
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            Double turnover = orderMapper.getTurnoverByReportDate(beginTime, endTime);
            turnover = turnover == null ? turnover = 0.0 : turnover;
            turnoverList.add(turnover);
        }

        //封装返回结果
        return TurnoverReportVO
                .builder()
                .dateList(StringUtils.join(dateList,","))
                .turnoverList(StringUtils.join(turnoverList,","))
                .build();
    }

    /**
     * 统计指定时间内的用户数
     * @param begin
     * @param end
     * @return
     */
    public UserReportVO getUserStatistics(LocalDate begin, LocalDate end) {
        //存放从begin到end的所有日期
        List<LocalDate> dateList = new ArrayList<>();
        //存放从begin到end的所有用户数
        List<Integer> userList = new ArrayList<>();
        //存放从begin到end的新增用户数
        List<Integer> newUserList = new ArrayList<>();

        for (LocalDate date = begin; !date.isAfter(end); date = date.plusDays(1)){
            dateList.add(date);
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);
            userList.add(userMapper.getNumberByReportDate(endTime));
            newUserList.add(userMapper.getNewUserByReportDate(beginTime,endTime));
        }

        return UserReportVO
                .builder()
                .dateList(StringUtils.join(dateList,","))
                .totalUserList(StringUtils.join(userList,","))
                .newUserList(StringUtils.join(newUserList,","))
                .build();
    }

    /**
     * 统计指定时间内的订单数
     * @param begin
     * @param end
     * @return
     */
    public OrderReportVO getOrdersStatistics(LocalDate begin, LocalDate end) {
        //LocalDate转换LocalDataTime
        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);

        //存放从begin到end的所有日期
        List<LocalDate> dateList = new ArrayList<>();

        //存放从begin到end的每日订单数
        List<Integer> orderCountList = new ArrayList<>();

        //存放从begin到end的每日有效订单数
        List<Integer> validOrderCountList = new ArrayList<>();

        //存放从begin到end的总订单数
        Integer totalOrderCount = orderMapper.countStatusByTime(beginTime,endTime,null);

        //存放从begin到end的有效订单数
        Integer validOrderCount = orderMapper.countStatusByTime(beginTime,endTime,5);

        //存放从begin到end的订单完成率
        Double orderCompletionRate = validOrderCount.doubleValue() / totalOrderCount;


        for (LocalDate date = begin; !date.isAfter(end); date = date.plusDays(1)){

            dateList.add(date);

            beginTime = LocalDateTime.of(date, LocalTime.MIN);
            endTime = LocalDateTime.of(date, LocalTime.MAX);

            orderCountList.add(orderMapper.countStatusByTime(beginTime,endTime,null));
            validOrderCountList.add(orderMapper.countStatusByTime(beginTime,endTime,5));
        }

        return OrderReportVO
                .builder()
                .dateList(StringUtils.join(dateList,","))
                .orderCountList(StringUtils.join(orderCountList,","))
                .validOrderCountList(StringUtils.join(validOrderCountList,","))
                .totalOrderCount(totalOrderCount)
                .validOrderCount(validOrderCount)
                .orderCompletionRate(orderCompletionRate)
                .build();
    }

    /**
     * 统计指定时间内的销量排名前十
     * @param begin
     * @param end
     * @return
     */
    public SalesTop10ReportVO getSalesTop10(LocalDate begin, LocalDate end) {

        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);

        List<String> nameList = new ArrayList<>();

        List<String> numberList = new ArrayList<>();

        List<GoodsSalesDTO> top10 = orderMapper.getTop10(beginTime,endTime);

        for (GoodsSalesDTO goodsSalesDTO : top10) {
            nameList.add(goodsSalesDTO.getName());
            numberList.add(goodsSalesDTO.getNumber().toString());
        }

        System.out.println(nameList.toString());
        System.out.println(numberList.toString());

        return SalesTop10ReportVO
                .builder()
                .nameList(StringUtils.join(nameList,","))
                .numberList(StringUtils.join(numberList,","))
                .build();
    }
}
