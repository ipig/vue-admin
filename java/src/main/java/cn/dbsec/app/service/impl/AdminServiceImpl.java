package cn.dbsec.app.service.impl;

import cn.dbsec.app.pojo.AppUser;
import cn.dbsec.app.pojo.PostInfo;
import cn.dbsec.app.pojo.SalesUser;
import cn.dbsec.app.pojo.UserMsg;
import cn.dbsec.app.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component
public class AdminServiceImpl implements AdminService {

    public static int INDEX_TYPE = 1;
    public static int PRODUCT_TYPE = 2;
    public static int FAQ_TYPE = 3;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    String getModifyTime() {
        return formatter.format(Calendar.getInstance().getTime());
    }

    private List<PostInfo> select(int type, int productType) {
        String sql = "SELECT * FROM posts";
        List<Object> args = new ArrayList<Object>();
        if (type != 0) {
            sql += " AND type = ?";
            args.add(type);
        }
        if (productType != 0) {
            sql += " AND productType = ?";
            args.add(productType);
        }
        if (args.size() > 0) {
            sql = sql.replaceFirst("AND", "WHERE");
        }
        return jdbcTemplate.query(sql, args.toArray(), new BeanPropertyRowMapper<>(PostInfo.class));
    }

    private void insert(PostInfo data) {
        String sql = "INSERT INTO posts(type, productType, title, content, modifyTime) VALUES (?,?,?,?,?)";
        Object[] args = {data.getType(), data.getProductType(), data.getTitle(), data.getContent(), getModifyTime()};
        jdbcTemplate.update(sql, args);
    }

    private void update(PostInfo data) {
        String sql = "UPDATE posts SET title=?,content=?";
        List<Object> args = new ArrayList<Object>();
        args.add(data.getTitle());
        args.add(data.getContent());
        if (data.getType() != 0) {
            sql += " ,type = ?";
            args.add(data.getType());
        }
        if (data.getProductType() != 0) {
            sql += " ,productType = ?";
        }

        sql += " ,modifyTime=?";
        args.add(getModifyTime());

        sql += " WHERE id=?";
        args.add(data.getId());
        jdbcTemplate.update(sql, args.toArray());
    }

    @Override
    public List<PostInfo> getIndexData() {
        return select(INDEX_TYPE, 0);
    }

    @Override
    public List<PostInfo> getProductData(int productType) {
        return select(PRODUCT_TYPE, productType);
    }

    @Override
    public List<PostInfo> getFaqData(int productType) {
        List<PostInfo> list = select(FAQ_TYPE, productType);
        if (list.size() == 0) {
            list = new ArrayList<PostInfo>();
            PostInfo info = new PostInfo();
            info.setId("0");
            //info.setTitle("未添加");
            //info.setContent("未添加");
            info.setProductType(productType);
            list.add(info);
        }
        return list;
    }

    @Override
    public void addIndexData(String title, String content) {
        PostInfo data = new PostInfo();
        data.setType(INDEX_TYPE);
        data.setTitle(title);
        data.setContent(content);
        insert(data);
    }

    @Override
    public void addProductData(int productType, String title, String content) {
        PostInfo data = new PostInfo();
        data.setType(PRODUCT_TYPE);
        data.setProductType(productType);
        data.setTitle(title);
        data.setContent(content);
        insert(data);
    }

    @Override
    public void modifyIndexData(String id, String title, String content) {
        PostInfo data = new PostInfo();
        data.setId(id);
        data.setTitle(title);
        data.setContent(content);
        update(data);
    }

    @Override
    public void modifyProductData(String id, String title, String content) {
        PostInfo data = new PostInfo();
        data.setId(id);
        data.setTitle(title);
        data.setContent(content);
        update(data);
    }

    @Override
    public void modifyFaqData(int productType, String title, String content) {
        PostInfo data = new PostInfo();
        data.setType(FAQ_TYPE);
        data.setTitle(title);
        data.setContent(content);

        // 为了app展示 保证只有一条FAQ数据
        PostInfo pi = getFaqData(productType).get(0);
        if ("0".equals(pi.getId())) {
            data.setProductType(productType);
            insert(data);
        } else {
            data.setId(pi.getId());
            update(data);
        }
    }

    public List<SalesUser> getSalesUsers() {
        return jdbcTemplate.query(
                "SELECT * FROM sales",
                new Object[]{},
                new BeanPropertyRowMapper<>(SalesUser.class));
    }

    public void addSalesUser(SalesUser params) {
        Object[] args = {params.getSid(), params.getIp(), params.getCompany()};
        jdbcTemplate.update("insert into sales(sid,ip,company) values (?,?,?)", args);
    }

    public void deleteSalesUser(String id) {
        Object[] args = {id};
        jdbcTemplate.update("delete from sales where id=?", args);
    }

}
