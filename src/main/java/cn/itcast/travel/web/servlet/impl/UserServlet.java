package cn.itcast.travel.web.servlet.impl;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import cn.itcast.travel.web.servlet.BaseServlet;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Random;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private UserService userService = new UserServiceImpl();
    private ResultInfo info = new ResultInfo();


    /**
     * 用户注册方法
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //0.先进行验证码校验
        String user_check = request.getParameter("check");
        String CHECKCODE_SERVER = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        HttpSession session = request.getSession();
        //保证验证码只能使用一次
        session.removeAttribute("CHECKCODE_SERVER");
        ResultInfo info = new ResultInfo();
        if (CHECKCODE_SERVER != null && CHECKCODE_SERVER.equalsIgnoreCase(user_check)) {
            //如果两个相同，再进行数据表单的
            //1.获取数据
            Map<String, String[]> map = request.getParameterMap();
            //2.封装对象
            User user = new User();
            try {
                BeanUtils.populate(user, map);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            //3.调用service完成注册

            boolean flag = userService.register(user);
            //4.响应结果

            if (flag) {
                //完成注册
                info.setFlag(true);
            } else {
                //注册失败
                info.setFlag(false);
                info.setErrorMsg("注册失败");
            }
        } else {
            //验证码错误
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
        }

        //将  info 序列化为jackjson 返回到客户端
        /*ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        //将json 数据写到客户端
        //设置content-type
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);*/
        writeValue(info, response);

    }

    /**
     * 用户登陆方法
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取信息
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        //判断用户名是否处于激活状态
        User all_user = userService.login(username, password);

        // 判断用户名是否存在
        ResultInfo info = new ResultInfo();
        if (all_user == null) {
            //说明用户名或密码错误
            info.setFlag(false);
            info.setErrorMsg("该用户尚未登陆请登陆");
        }
        //判断用户是否激活
        if (all_user != null && "Y".equals(all_user.getStatus())) {
            //用户已经激活
            //进行登陆
            request.getSession().setAttribute("all_user", all_user);
            info.setFlag(true);
        }
        if (all_user != null && "N".equals(all_user.getStatus())) {
            //用户已经激活
            //进行登陆
            info.setFlag(false);
            info.setErrorMsg("该用户尚未激活，请激活");
        }
        //设置响应数据
        /*response.setContentType("application/json;charset=utf-8");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        response.getWriter().write(json);*/
        writeValue(info, response);
    }

    /**
     * 查找用户方法
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        Object all_user = request.getSession().getAttribute("all_user");
        /*ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(all_user);
        response.getWriter().write(json);*/
        writeValue(all_user, response);
    }

    /**
     * 用户退出登陆方法
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //销毁session
        request.getSession().invalidate();
        //跳转页面
        response.sendRedirect(request.getContextPath() + "/login.html");
    }

    /**
     * 验证码生成方法
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void checkCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //服务器通知浏览器不要缓存
        response.setHeader("pragma", "no-cache");
        response.setHeader("cache-control", "no-cache");
        response.setHeader("expires", "0");

        //在内存中创建一个长80，宽30的图片，默认黑色背景
        //参数一：长
        //参数二：宽
        //参数三：颜色
        int width = 80;
        int height = 30;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        //获取画笔
        Graphics g = image.getGraphics();
        //设置画笔颜色为灰色
        g.setColor(Color.GRAY);
        //填充图片
        g.fillRect(0, 0, width, height);

        //产生4个随机验证码，12Ey
        String checkCode = getCheckCode();
        //将验证码放入HttpSession中
        request.getSession().setAttribute("CHECKCODE_SERVER", checkCode);

        //设置画笔颜色为黄色
        g.setColor(Color.YELLOW);
        //设置字体的小大
        g.setFont(new Font("黑体", Font.BOLD, 24));
        //向图片上写入验证码
        g.drawString(checkCode, 15, 25);

        //将内存中的图片输出到浏览器
        //参数一：图片对象
        //参数二：图片的格式，如PNG,JPG,GIF
        //参数三：图片输出到哪里去
        ImageIO.write(image, "PNG", response.getOutputStream());
    }

    /**
     * 产生4位随机字符串
     */
    private String getCheckCode() {
        String base = "0123456789ABCDEFGabcdefg";
        int size = base.length();
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i <= 4; i++) {
            //产生0到size-1的随机值
            int index = r.nextInt(size);
            //在base字符串中获取下标为index的字符
            char c = base.charAt(index);
            //将c放入到StringBuffer中去
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * 我的收藏
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void myfavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //我的收藏
        //1.在session 中获取当前登陆的用户信息
        User user = (User) request.getSession().getAttribute("all_user");
        //2.
        List<Route> myfavorite = userService.myfavorite(user.getUid());
        if (myfavorite.size() == 0) {
            //说明该用户尚未进行收藏 , 没收藏为false
            info.setFlag(false);
            info.setErrorMsg("您尚未收藏，请收藏");
        } else {
            info.setFlag(true);
            info.setData(myfavorite);
        }
        writeValue(info, response);
    }


}
