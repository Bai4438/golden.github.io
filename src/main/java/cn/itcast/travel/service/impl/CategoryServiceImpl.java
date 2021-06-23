package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImpl();

    /**
     * 列表分类
     *
     * @return
     */
    @Override
    public List<Category> findAllSort() {
        //1.从redis中查询
        //1.1获取Jedis客户端
        Jedis jedis = JedisUtil.getJedis();
        //1.2可使用sortedSet 排序查询
//        Set<String> categories = jedis.zrange("category", 0, -1);
        //1.3查询sortedset 中的分数（cid） 和值（cname）
        Set<Tuple> categories = jedis.zrangeWithScores("category", 0, -1);
        //2.判断查询的集合是否为空
        List<Category> cs = null;
        if (categories == null || categories.size() == 0) {
            //3.如果为空，需要从数据库查询，再将数据存入redis中
            //3.1从数据库查询
            cs = categoryDao.findAllSort();
            //3.2将集合数据存储到redis中的category 的key
            for (int i = 0; i < cs.size(); i++) {
                jedis.zadd("category", cs.get(i).getCid(), cs.get(i).getCname());
            }
        }else {
            cs = new ArrayList<Category>();
            for (Tuple tuple : categories) {
                Category category = new Category();
                category.setCname(tuple.getElement());
                category.setCid((int)tuple.getScore());
                cs.add(category);
            }
        }
        //3.如果为空，需要从数据库查询，再将数据存入redis

        //4.如果为空，直接返回
        return cs;
    }
}
