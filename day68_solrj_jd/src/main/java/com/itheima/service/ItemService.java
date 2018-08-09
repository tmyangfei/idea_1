package com.itheima.service;

import com.itheima.domain.ResultModel;

/**
 * @Author: yangfei
 * @Date: 2018/8/6 21:00
 * @Description:
 */

public interface ItemService {

    ResultModel queryIndex(String queryString, String catelog_name, String price, String sort, Integer page, Integer rows);
}
