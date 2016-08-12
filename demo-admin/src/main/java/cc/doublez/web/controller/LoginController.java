package cc.doublez.web.controller;

import cc.doublez.domain.pojo.user.User;
import cc.doublez.platform.cache.RedisSessionDao;
import cc.doublez.platform.shiro.manager.ShiroManager;
import cc.doublez.service.login.ILoginService;
import cc.doublez.util.StringUtil;
import cc.doublez.util.ValidateCode;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by yz on 2016/7/27
 */
@Controller
public class LoginController {
    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private ILoginService loginService;
    @Autowired
    private RedisSessionDao redisSessionDao;
    @RequestMapping(value = "/x3i9ze7w/login",method = RequestMethod.GET)
    public ModelAndView login(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("login");
       /* if(request.getSession().getAttribute("loginUser")!=null){
            mav.setViewName("redirect:/x3i9ze7w/user/add");
        }*/
        return mav;
    }

    @RequestMapping(value = "/x3i9ze7w/login",method = RequestMethod.POST)
    public ModelAndView dologin(HttpServletRequest request,@Valid User user, BindingResult result){
       //ModelAndView mav = new ModelAndView("redirect:/x3i9ze7w/success");
        ModelAndView mav = new ModelAndView();
        /*if (loginService.userLogin(user.getPhone(),user.getPassword())){
            request.getSession().setAttribute("loginUser",user);
            return mav;
        }
        if (result.hasErrors()){
            List<ObjectError> errorList = result.getAllErrors();
            for(ObjectError error : errorList){
                System.out.println(error.getDefaultMessage());
            }
        }
        mav.setViewName("redirect:/x3i9ze7w/login");*/
        /*String code = (String)  request.getSession().getAttribute("validateCode");
        String submitCode = WebUtils.getCleanParam(request, "validateCode");
        if (StringUtil.isEmpty(submitCode) || !StringUtils.equals(code,submitCode.toLowerCase())) {
            mav.setViewName("redirect:/x3i9ze7w/login");
            return mav;
        }*/
        UsernamePasswordToken token = new UsernamePasswordToken(user.getPhone(),user.getPassword());
        Subject currentUser = SecurityUtils.getSubject();
        /*if (currentUser.isAuthenticated()){
            return mav;
        }*/
        try {
            currentUser.login(token);
            String url = ShiroManager.getSavedUrl(request);
            mav.setViewName("redirect:"+url);
        }catch (Exception e){
            System.out.println("登陆失败");
            e.printStackTrace();
        }
        return mav;
    }

    @RequestMapping(value = "/x3i9ze7w/logout",method = RequestMethod.GET)
    public ModelAndView loginout(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("login");
        //request.getSession().removeAttribute("loginUser");
        SecurityUtils.getSubject().logout();
        return mav;
    }

    @RequestMapping(value = "/x3i9ze7w/unauthorized",method = RequestMethod.GET)
    public ModelAndView unauthorized(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("unauthorized");
        return mav;
    }

    @RequestMapping(value = "/x3i9ze7w/success",method = RequestMethod.GET)
    public ModelAndView success(){
        ModelAndView mav = new ModelAndView("success");
        mav.addObject("message","登陆成功");
        return mav;
    }

    @Autowired
    private ShiroManager shiroManager;
    @RequestMapping(value = "/x3i9ze7w/init",method = RequestMethod.GET)
    public ModelAndView init(){
        ModelAndView mav = new ModelAndView("redirect:/x3i9ze7w/success");
        shiroManager.initFilterChains();
        return mav;
    }

    /**
     * 生成验证码
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/x3i9ze7w/validateCode")
    public void validateCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "no-cache");
        String verifyCode = ValidateCode.generateTextCode(ValidateCode.TYPE_NUM_ONLY, 4, null);
        request.getSession().setAttribute("validateCode", verifyCode);
        response.setContentType("image/jpeg");
        BufferedImage bim = ValidateCode.generateImageCode(verifyCode, 90, 30, 3, true, Color.WHITE, Color.BLACK, null);
        ImageIO.write(bim, "JPEG", response.getOutputStream());
    }
}
