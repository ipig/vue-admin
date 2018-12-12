package cn.dbsec.app.service;

import cn.dbsec.app.pojo.AppUser;
import cn.dbsec.app.pojo.PostInfo;
import cn.dbsec.app.pojo.SalesUser;
import cn.dbsec.app.pojo.UserMsg;

import java.util.List;

public interface AdminService {

    /**
     * 取得首页数据
     */
    public List<PostInfo> getIndexData();

    /**
     * 取得产品介绍数据
     */
    public List<PostInfo> getProductData(int productType);

    /**
     * 取得常见问题数据
     */
    public List<PostInfo> getFaqData(int productType);

    /**
     * 添加首页数据
     *
     * @param title   标题
     * @param content 内容
     */
    public void addIndexData(String title, String content);

    /**
     * 添加产品介绍数据
     *
     * @param productType 产品类型
     * @param title       标题
     * @param content     内容
     */
    public void addProductData(int productType, String title, String content);

    /**
     * 修改数据
     *
     * @param id      数据ID
     * @param title   标题
     * @param content 内容
     */
    public void modifyIndexData(String id, String title, String content);

    /**
     * 修改数据
     *
     * @param id      数据ID
     * @param title   标题
     * @param content 内容
     */
    public void modifyProductData(String id, String title, String content);

    /**
     * 修改数据
     *
     * @param productType 产品类型
     * @param title       标题
     * @param content     内容
     */
    public void modifyFaqData(int productType, String title, String content);

    public  List<SalesUser> getSalesUsers();

    public  void addSalesUser(SalesUser params);

    public  void deleteSalesUser(String id);

}
