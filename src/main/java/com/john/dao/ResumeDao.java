package com.john.dao;

import com.john.pojo.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author:wenwei
 * @date:2020/03/14
 * @description:
 */

/**
 * 一个接口可以继承多个接口
 * 一个符合SpringdataJpa要求的dao层接口需要继承JpaRepository和JpaSpecificationExecutor
 * JpaRepository<操作的实体类型，主键类型>
 *      封装了基本的CRUD的操作
 *
 * JpaSpecificationExecutor <操作的实体类型>
 *     只有查询的接口
 *     封装了复杂的查询（分页，排序等）
 *
 *
 */
public interface ResumeDao extends JpaRepository<Resume,Integer>, JpaSpecificationExecutor<Resume> {

    @Query("from Resume where id =?1 and name=?2")
    List<Resume> findByJpql(Integer id,String name);

    /**
     * 使用原生的sql查询，属性设置为true
     * @param id
     * @param name
     * @return
     */
    @Query(value = "select * from tb_resume where name like ?",nativeQuery = true)
    List<Resume> findBySql(String name);

    /**
     * 使用方法命名规则查询
     * 按照name模糊查询（like）
     * findBy 开头
     *  - 属性名（首字母大学）
     *      查询方式（）
     * @param name
     * @return
     */
    List<Resume> findByNameLike(String name);
}
