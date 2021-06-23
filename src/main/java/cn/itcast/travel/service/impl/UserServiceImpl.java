package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    /**
     * 注册用户的方法
     *
     * @param user
     * @return
     */
    @Override
    public boolean register(User user) {
        //调用dao层的方法
        //1.判断该用户名是否存在
        // true 表示该用户名存在，  false 表示用户名不存在
        User flag = userDao.findByUsername(user.getUsername());
        if (flag == null) {
            //说明用户名不存在, 把该用户保存在数据库中
            //设置激活码，唯一字符串
            user.setCode(UuidUtil.getUuid());
            //N 代表未激活
            user.setStatus("N");
            userDao.save(user);
            String context = "<a href='http://localhost/travel/active?code=" + user.getCode() + "'>点击激活【黑马旅游网】</a>";

            MailUtils.sendMail(user.getEmail(), context, "激活邮件");
            return true;
        }
        return false;
    }

    @Override
    public boolean active(String code) {
        //1.根据激活码查询用户对象
        User user = userDao.findByCode(code);
        if (user != null) {
            //2.调用dao层来修改激活状态
            userDao.updateStatus(user);
            return true;
        } else {
            return false;
        }

    }

    @Override
    public User login(String username, String password) {
        return userDao.findUsernameAndPassword(username,password);
    }

    @Override
    public List<Route> myfavorite(int uid) {
        return userDao.myfavorite(uid);
    }


    /**
     * 登陆方法
     *
     * @param user
     * @return
     */

}
