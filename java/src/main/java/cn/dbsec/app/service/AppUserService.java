package cn.dbsec.app.service;

import cn.dbsec.app.pojo.AppUser;
import cn.dbsec.app.pojo.PostInfo;
import cn.dbsec.app.pojo.UserMsg;

import java.util.List;

public interface AppUserService {

    public List<AppUser> getAppUsers();

    public List<UserMsg> getOldChatLog(String userId, int logId, int size);

    public List<UserMsg> getNewChatLog(String userId, int logId);

    public void addChatLog(String userId, String content);

}
