package com.neo.web;

import com.neo.entity.City;
import com.neo.param.CityParam;
import com.neo.repository.CityRepository;
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

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
public class CityController {
    @Autowired
    private CityRepository cityRepository;

    @RequestMapping("/city")
    public String index() {
        return "redirect:city/list";
    }

    @RequestMapping("city/list")
    public String list(Model model,@RequestParam(value = "page", defaultValue = "0") Integer page,
                       @RequestParam(value = "size", defaultValue = "6") Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(page, size, sort);
        Page<City> list=cityRepository.findList(pageable);
        model.addAttribute("list", list);
        return "city/list";
    }

    @RequestMapping("city/add")
    public String toAdd() {
        return "city/add";
    }

    /**
     * 新增写入数据库记录
     * @param cityParam
     * @param result
     * @param model
     * @return
     */
    @RequestMapping("city/insert")
    public String insert(@Valid CityParam cityParam,BindingResult result, ModelMap model) {
        String errorMsg="";
        if(result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            for (ObjectError error : list) {
                errorMsg=errorMsg + error.getCode() + "-" + error.getDefaultMessage() +";";
            }
            model.addAttribute("errorMsg",errorMsg);
            return "city/add";
        }
        City u= cityRepository.findByName(cityParam.getName());
        if(u!=null){
            model.addAttribute("errorMsg","记录已存在!");
            return "city/add";
        }
        City obj=new City();
        BeanUtils.copyProperties(cityParam,obj);
        cityRepository.save(obj);
        //redirect,不要带前缀，直接写  page名称即可
        //page中也不要带前缀，直接写  page名称即可
        return "redirect:list";
    }


    @RequestMapping("city/edit")
    public String toEdit(Model model,Long id) {
        City obj=cityRepository.findById(id);
        model.addAttribute("vo", obj);
        return "city/edit";
    }


    /**
     * 更新写入数据库记录
     * @param cityParam
     * @param result
     * @param model
     * @return
     */
    @RequestMapping("city/update")
    public String update(@Valid CityParam cityParam, BindingResult result, ModelMap model) {
        String errorMsg="";
        if(result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();
            for (ObjectError error : list) {
                errorMsg=errorMsg + error.getCode() + "-" + error.getDefaultMessage() +";";
            }
            model.addAttribute("errorMsg",errorMsg);
            model.addAttribute("city", cityParam);
            return "city/edit";
        }

        City obj=new City();
        BeanUtils.copyProperties(cityParam,obj);
        cityRepository.save(obj);
        return "redirect:list";
    }

    @RequestMapping("city/delete")
    public String delete(long id) {
        cityRepository.delete(id);
        return "redirect:list";
    }
}
