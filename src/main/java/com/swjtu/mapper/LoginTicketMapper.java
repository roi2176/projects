package com.swjtu.mapper;

import com.swjtu.entity.LoginTicket;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
@Deprecated
public interface LoginTicketMapper {
    @Insert("insert into login_ticket(user_id, ticket, status, expired) VALUES " +
            "(#{userId}, #{ticket},#{status},#{expired})")
    int insertLoginTicket(LoginTicket loginTicket);

    @Select("select * from login_ticket where ticket = #{ticket}")
    LoginTicket selectByTicket(String ticket);

    @Update("update login_ticket set status = #{status} where ticket = #{ticket}")
    int updateStatus(String ticket, int status);
}
