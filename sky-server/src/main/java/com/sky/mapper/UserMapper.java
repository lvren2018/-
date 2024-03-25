package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;

@Mapper
public interface UserMapper {

    /**
     * 根据openid查询用户
     * @param openid
     * @return
     */
    @Select("select * from user where openid = #{openid}")
    User getByOpenid(String openid);

    /**
     * 插入数据
     * @param user
     * @return
     */
    void insert(User user);

    /**
     *
     * @param id
     * @return
     */
    @Select("select * from user where id = #{id}")
    User getById(Long id);

    @Select("select count(id) from user where create_time < #{beginTime}")
    Integer getNumberByReportDate(LocalDateTime endTime);

    @Select("select count(id) from user where create_time >= #{beginTime} and create_time <= #{endTime}")
    Integer getNewUserByReportDate(LocalDateTime beginTime, LocalDateTime endTime);
}
