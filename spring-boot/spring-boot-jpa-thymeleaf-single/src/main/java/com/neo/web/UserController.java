package com.neo.web;

import com.neo.entity.User;
import com.neo.param.UserParam;
import com.neo.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/user")
    public String index() {
        //如下这个不可为 redirect:list
        return "redirect:user/list";
    }

    @RequestMapping("user/list")
    public String list(Model model,@RequestParam(value = "page", defaultValue = "0") Integer page,
                       @RequestParam(value = "size", defaultValue = "6") Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(page, size, sort);
        Page<User> list = userRepository.findList(pageable);
        model.addAttribute("list", list);
        return "user/list";
    }

    @RequestMapping("user/add")
    public String toAdd() {
        return "user/add";
    }

    /**
     * 新增写入数据库记录
     * @param userParam
     * @param result
     * @param model
     * @return
     */
    @RequestMapping("user/insert")
    public String insert(@Valid UserParam userParam,BindingResult result, ModelMap model) {
        String errorMsg="";
        if(result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            for (ObjectError error : list) {
                errorMsg=errorMsg + error.getCode() + "-" + error.getDefaultMessage() +";";
            }
            model.addAttribute("errorMsg",errorMsg);
            return "user/add";
        }
        User u= userRepository.findByUserName(userParam.getUserName());
        if(u!=null){
            model.addAttribute("errorMsg","用户已存在!");
            return "user/add";
        }
        User obj=new User();
        BeanUtils.copyProperties(userParam,obj);
        obj.setRegTime(new Date());
        userRepository.save(obj);
        //redirect,不要带前缀，直接写  page名称即可
        //page中也不要带前缀，直接写  page名称即可
        return "redirect:list";
    }

    /**
     * 跳转到编辑界面
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("user/edit")
    public String toEdit(Model model,Long id) {
        User obj=userRepository.findById(id);
        model.addAttribute("vo", obj);
        return "user/edit";
    }

    /**
     * 更新写入数据库记录
     * @param userParam
     * @param result
     * @param model
     * @return
     */
    @RequestMapping("user/update")
    public String update(@Valid UserParam userParam, BindingResult result,ModelMap model) {
        String errorMsg="";
        if(result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            for (ObjectError error : list) {
                errorMsg=errorMsg + error.getCode() + "-" + error.getDefaultMessage() +";";
            }
            model.addAttribute("errorMsg",errorMsg);
            model.addAttribute("user", userParam);
            return "user/edit";
        }

        User obj=new User();
        BeanUtils.copyProperties(userParam,obj);
        obj.setRegTime(new Date());
        userRepository.save(obj);
        return "redirect:list";
    }

    /**
     * 删除数据库记录
     * @param id
     * @return
     */
    @RequestMapping("user/delete")
    public String delete(Long id) {
        userRepository.delete(id);
        return "redirect:list";
    }
}
