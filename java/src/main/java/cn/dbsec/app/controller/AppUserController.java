package cn.dbsec.app.controller;

import cn.dbsec.app.base.ResponseBean;
import cn.dbsec.app.pojo.AppUser;
import cn.dbsec.app.pojo.UserMsg;
import cn.dbsec.app.service.AppUserService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("appUser")
@Controller
public class AppUserController {

    @Autowired
    AppUserService appUserService;

    @RequestMapping("/getAppUsers")
    @ResponseBody
    public ResponseBean getAppUsers() {
        List<AppUser> data = appUserService.getAppUsers();
        return ResponseBean.success(data);
    }

    @RequestMapping("/getOldChatLog")
    @ResponseBody
    public ResponseBean getOldChatLog(@RequestBody JSONObject params) {
        String userId = params.getString("userId");
        int logId = params.getIntValue("logId");
        List<UserMsg> data = appUserService.getOldChatLog(userId, logId, 20);
        return ResponseBean.success(data);
    }

    @RequestMapping("/getNewChatLog")
    @ResponseBody
    public ResponseBean getNewChatLog(@RequestBody JSONObject params) {
        String userId = params.getString("userId");
        int logId = params.getIntValue("logId");
        if (logId == 0) {
            return ResponseBean.success(null);
        }
        List<UserMsg> data = appUserService.getNewChatLog(userId, logId);
        return ResponseBean.success(data);
    }

    @RequestMapping("/addChatLog")
    @ResponseBody
    public ResponseBean addChatLog(@RequestBody JSONObject params) {
        String userId = params.getString("userId");
        String content = params.getString("content");
        appUserService.addChatLog(userId, content);
        return ResponseBean.success(null);
    }

}
