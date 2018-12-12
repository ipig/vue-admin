package cn.dbsec.app.service.impl;

import cn.dbsec.app.pojo.AppUser;
import cn.dbsec.app.pojo.UserMsg;
import cn.dbsec.app.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Component
public class AppUserServiceImpl implements AppUserService {

    public static int INDEX_TYPE = 1;
    public static int PRODUCT_TYPE = 2;
    public static int FAQ_TYPE = 3;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    String getModifyTime() {
        return formatter.format(Calendar.getInstance().getTime());
    }

    public List<AppUser> getAppUsers() {
        return jdbcTemplate.query(
                "SELECT *,ifnull(count,0) AS newLogCount FROM `user` t1 LEFT JOIN (SELECT userId,count(*) AS count FROM user_msg WHERE adminReaded=0 AND replyUserName='' GROUP BY userId) t2 ON t1.id=t2.userId",
                new Object[]{},
                new BeanPropertyRowMapper<>(AppUser.class));
    }

    public List<UserMsg> getOldChatLog(String userId, int logId, int size) {
        if (logId == 0) {
            return jdbcTemplate.query(
                    "SELECT * FROM (SELECT * FROM `user_msg` where userId=? order by id desc LIMIT 0,?) t1 ORDER BY t1.id",
                    new Object[]{userId, size},
                    new BeanPropertyRowMapper<>(UserMsg.class));
        }
        return jdbcTemplate.query(
                "SELECT * FROM (SELECT * FROM `user_msg` where userId=? AND id <= ? order by id desc LIMIT 0,?) t1 ORDER BY t1.id",
                new Object[]{userId, logId, size},
                new BeanPropertyRowMapper<>(UserMsg.class));
    }

    public List<UserMsg> getNewChatLog(String userId, int logId) {
        return jdbcTemplate.query(
                "SELECT * FROM `user_msg` where userId=? AND id > ? order by id desc",
                new Object[]{userId, logId},
                new BeanPropertyRowMapper<>(UserMsg.class));
    }

    public void addChatLog(String userId, String content) {
        Object[] uargs = {userId};
        jdbcTemplate.update("UPDATE user_msg SET adminReaded=1 where userId=?", uargs);

        String sql = "INSERT INTO user_msg(userId, content,replyUserName, time) VALUES (?,?,?,?)";
        Object[] args = {userId, content, "admin", getModifyTime()};
        jdbcTemplate.update(sql, args);
    }
}
