package cn.dbsec.app.controller;

import cn.dbsec.app.base.ResponseBean;
import cn.dbsec.app.pojo.PostInfo;
import cn.dbsec.app.pojo.SalesUser;
import cn.dbsec.app.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("admin")
@Controller
public class AdminController {

    @Autowired
    AdminService adminService;

    @RequestMapping("/getIndexData")
    @ResponseBody
    public ResponseBean getIndexData() {
        List<PostInfo> data = adminService.getIndexData();
        return ResponseBean.success(data);
    }

    @RequestMapping("/getProductData")
    @ResponseBody
    public ResponseBean getProductData(@RequestBody PostInfo params) {
        List<PostInfo> data = adminService.getProductData(params.getProductType());
        return ResponseBean.success(data);
    }

    @RequestMapping("/getFaqData")
    @ResponseBody
    public ResponseBean getFaqData(@RequestBody PostInfo params) {
        List<PostInfo> data = adminService.getFaqData(params.getProductType());
        return ResponseBean.success(data);
    }

    /**
     * 添加首页数据
     *
     * @param title   标题
     * @param content 内容
     */
    @RequestMapping("/addIndexData")
    @ResponseBody
    public ResponseBean addIndexData(@RequestBody PostInfo params) {
        adminService.addIndexData(params.getTitle(), params.getContent());
        return ResponseBean.success(null);
    }

    /**
     * 添加产品介绍数据
     *
     * @param productType 产品类型
     * @param title       标题
     * @param content     内容
     */
    @RequestMapping("/addProductData")
    @ResponseBody
    public ResponseBean addProductData(@RequestBody PostInfo params) {
        adminService.addProductData(params.getProductType(), params.getTitle(), params.getContent());
        return ResponseBean.success(null);
    }

    /**
     * 修改数据
     *
     * @param id      数据ID
     * @param title   标题
     * @param content 内容
     */
    @RequestMapping("/modifyIndexData")
    @ResponseBody
    public ResponseBean modifyIndexData(@RequestBody PostInfo params) {
        adminService.modifyIndexData(params.getId(), params.getTitle(), params.getContent());
        return ResponseBean.success(null);
    }

    /**
     * 修改数据
     *
     * @param id      数据ID
     * @param title   标题
     * @param content 内容
     */
    @RequestMapping("/modifyProductData")
    @ResponseBody
    public ResponseBean modifyProductData(@RequestBody PostInfo params) {
        adminService.modifyProductData(params.getId(), params.getTitle(), params.getContent());
        return ResponseBean.success(null);
    }

    /**
     * 修改数据
     *
     * @param id      数据ID
     * @param title   标题
     * @param content 内容
     */
    @RequestMapping("/modifyFaqData")
    @ResponseBody
    public ResponseBean modifyFaqData(@RequestBody PostInfo params) {
        adminService.modifyFaqData(params.getProductType(), params.getTitle(), params.getContent());
        return ResponseBean.success(null);
    }

    @RequestMapping("/getSalesUsers")
    @ResponseBody
    public ResponseBean getSalesUsers() {
        List<SalesUser> data = adminService.getSalesUsers();
        return ResponseBean.success(data);
    }

    @RequestMapping("/addSalesUser")
    @ResponseBody
    public ResponseBean addSalesUser(@RequestBody SalesUser params) {
        adminService.addSalesUser(params);
        return ResponseBean.success(null);
    }

    @RequestMapping("/delSalesUser")
    @ResponseBody
    public ResponseBean delSalesUser(@RequestBody SalesUser params) {
        adminService.deleteSalesUser(params.getId());
        return ResponseBean.success(null);
    }

}
