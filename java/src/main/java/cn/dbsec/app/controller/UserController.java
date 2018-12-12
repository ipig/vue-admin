package cn.dbsec.app.controller;

import cn.dbsec.app.base.ResponseBean;
import cn.dbsec.app.pojo.PostInfo;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("user")
@Controller
public class UserController {

    @RequestMapping("/info")
    @ResponseBody
    public ResponseBean info() {
        Map<String, Object> data = new HashMap<>();
        Object[] roles = {"admin"};
        data.put("roles", roles);
        return ResponseBean.success(data);
    }

    @RequestMapping("/login")
    @ResponseBody
    public ResponseBean login(@RequestBody JSONObject params) {
        String username = params.getString("username");
        String password = params.getString("password");
        if("admin".equals(username) && "Schina1234".equals(password)){
            String token = RandomStringUtils.randomAlphabetic(16);
            JSONObject data = new JSONObject();
            data.put("token",token);
            return ResponseBean.success(data);
        }
        return ResponseBean.error("登录失败");
    }
}
