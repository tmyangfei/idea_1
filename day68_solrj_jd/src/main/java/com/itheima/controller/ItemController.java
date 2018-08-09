package com.itheima.controller;

import com.itheima.domain.ResultModel;
import com.itheima.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: yangfei
 * @Date: 2018/8/6 20:03
 * @Description:
 */
@RequestMapping("/item")
@Controller
public class ItemController {

    // 注入service
    @Autowired
    private ItemService itemService;

    @RequestMapping("/list")
    public String searchItem(
        String queryString,
        String catelog_name,
        String price,
        @RequestParam(defaultValue = "1")String sort,
        @RequestParam(defaultValue = "1")Integer page,
        @RequestParam(defaultValue = "20")Integer rows,
        Model model
    ){
        ResultModel resultModel = itemService.queryIndex(
                queryString,catelog_name,price,sort,page,rows
        );
        // 保存到域中,进行页面回显
        model.addAttribute("queryString",queryString);
        model.addAttribute("catelog_name",catelog_name);
        model.addAttribute("price",price);
        model.addAttribute("sort",sort);
        model.addAttribute("result",resultModel);
        return "product_list";
    }
}
