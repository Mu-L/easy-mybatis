package top.zuoyu.mybatis.autoconfigure;

import org.apache.ibatis.session.SqlSession;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import top.zuoyu.mybatis.aspectj.cglib.Mappers;

/**
 * Mapper加载自动配置 .
 *
 * @author: zuoyu
 * @create: 2021-11-14 15:32
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(EasyMybatisAutoConfiguration.class)
public class MapperInitAutoConfiguration {

    public MapperInitAutoConfiguration(SqlSession sqlSession) {
        System.out.println("-------------------MapperInitAutoConfiguration-----------------------");
//        Mappers.init(sqlSession);
    }
}
