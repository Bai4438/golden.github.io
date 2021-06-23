package cn.itcast.travel.service;

import cn.itcast.travel.domain.Category;

import java.util.List;

public interface CategoryService {
    /**
     * 列表分类
     * @return
     */
    List<Category> findAllSort();
}
