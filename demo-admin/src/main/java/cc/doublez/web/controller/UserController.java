package cc.doublez.web.controller;

import cc.doublez.domain.pojo.user.User;
import cc.doublez.platform.annotation.ControllerLog;
import cc.doublez.platform.jms.Publisher;
import cc.doublez.service.jms.ISendService;
import cc.doublez.service.user.IUserService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by yz on 2015/12/23
 */
@Controller
@RequestMapping(value = "/x3i9ze7w/user")
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private ISendService sendService;
    @Autowired
    private Publisher topicSender;

    @RequestMapping(value = "/add",method = RequestMethod.GET)
    //@ControllerLog(description = "添加用户")
    public ModelAndView add(){
        ModelAndView mv = new ModelAndView("user/add");
        userService.testAspect("test");
        return mv;
    }

    /**
     * 数据校验
     * @param user
     * @param result
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String save(@Valid User user, BindingResult result){
        this.userService.insert(user);
        if (result.hasErrors()){
            List<ObjectError> errorList = result.getAllErrors();
            for(ObjectError error : errorList){
                System.out.println(error.getDefaultMessage());
            }
        }
        return "redirect:/x3i9ze7w/user/list";
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ModelAndView list(){
        ModelAndView mv = new ModelAndView("user/list");
        List<User> list = this.userService.list();
        mv.addObject("userList",list);
        return mv;
    }

    /**
     * 测试Ajax
     * @param id
     * @return
     */
    @RequestMapping(value = "/testAjax")
    @ResponseBody
    public  User testAjax(@RequestParam(value = "id",required = false) Integer id){
        System.out.println(id);
        User user = new User();
        user.setAge(22);
        return  user;
    }

    /**
     * 测试文件上传
     */
    @RequestMapping("/fileupload")
    public void upload(MultipartHttpServletRequest request, HttpServletResponse response) throws IllegalStateException, IOException {
        String url = "/x3i9ze7w/user/listFile";
        //request.getFile();
        List<MultipartFile> fileList = request.getFiles("file");
        for(MultipartFile file:fileList){
            String fileName = file.getOriginalFilename();
            String localPath = "D:/temp/" + fileName;
            File newFile = new File(localPath);
            //上传的文件写入到指定的文件中
            if (!newFile.exists()){
                newFile.mkdirs();
            }
            file.transferTo(newFile);
        }
       /* //获取解析器
        CommonsMultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断是否是文件
        if(resolver.isMultipart(request)){
            //进行转换
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)(request);
            //获取所有文件名称
            Iterator<String> it = multiRequest.getFileNames();
            while(it.hasNext()){
                //根据文件名称取文件
                MultipartFile file = multiRequest.getFile(it.next());
                String fileName = file.getOriginalFilename();
                String localPath = "D:/temp/" + fileName;
                File newFile = new File(localPath);
                //上传的文件写入到指定的文件中
                if (!newFile.exists()){
                    newFile.mkdirs();
                }
                file.transferTo(newFile);
            }*/
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().print("<script type='text/javascript'>alert('上传成功！'); window.location.href='"
                    + request.getContextPath() + url + "';</script>");
        //}
    }

    /**
     * 列出文件夹所在文件
     * @return
     */
    @RequestMapping(value = "/listFile",method = RequestMethod.GET)
    public ModelAndView listFile(){
        ModelAndView mv = new ModelAndView("user/file");
        String localPath = "D:/temp/";
        File file = new File(localPath);
        mv.addObject("fileList",file.listFiles());
        return mv;
    }

    /**
     * 文件下载
     */
    @RequestMapping(value = "/download")
    public ResponseEntity<byte[]> download(HttpServletRequest request,
            @RequestParam("name") String name,
            @RequestParam("path") String path)throws IOException {
        File file = new File(path);
        String dfileName = new String(name.getBytes("UTF-8"), "iso8859-1");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", dfileName);
        return new ResponseEntity(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }


   /**
     * jms
     */
    @RequestMapping(value = "/jms",method = RequestMethod.GET)
    public ModelAndView jms(){
        ModelAndView mv = new ModelAndView("user/add");
        User user = new User();
        user.setPhone("test");
        user.setAge(11);
        sendService.send(user);

        //topicSender.send("test.topic","测试topic");
        return mv;
    }
}

