import com.john.dao.ResumeDao;
import com.john.pojo.Resume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.List;
import java.util.Optional;

/**
 * @author:wenwei
 * @date:2020/03/14
 * @description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class ResumeDaoTest {

    @Autowired
    private ResumeDao resumeDao;

    @Test
    public void testFindAll() {


        List<Resume> all = resumeDao.findAll();
        for (int i = 0; i < all.size(); i++) {
            System.out.println(all.get(i));
        }
    }

    @Test
    public void testFindByID() {
        Optional<Resume> byId = resumeDao.findById(1);
        System.out.println(byId.get());
    }

    @Test
    public void testFindOne() {
        Resume resume = new Resume();
        resume.setId(1);
        resume.setName("t");
        Example<Resume> example = Example.of(resume);
        Optional<Resume> byId = resumeDao.findOne(example);

        System.out.println(byId.get());
    }

    @Test
    public void testUpdate() {
        Resume resume = new Resume();
        resume.setId(1);
        resume.setName("testSave");

        resumeDao.save(resume);

    }

    @Test
    public void testSave() {
        Resume resume = new Resume();
        resume.setName("testupdate");

        resumeDao.save(resume);

    }

    /**
     * 1: 调用继承接口里面的方法实现
     * 2； 可以引入jpql (jpql查询语言)进行查询
     * jpql语句类似与sql，只不过sql操作的是数据表和字断
     * 而jpql操作的是对象和属性
     * 比如（from Resume where id = (HQL )）
     *
     * 3: 引入原生的sql语句
     * 4: 可以在接口中自定义方法，按照方法名，查询（DDD），这种方法命名规则查询，
     * 5: 动态查询的方式
     *         service和dao层的条件不确定 把service拿到的条件封装成一个对象传递给dao层面
     *         这个对象就是specification（对条件的一个封装）
     *
     *
     *
     *
     *              //根据条件查询单个对象
     *             Optional<T> findOne(@Nullable Specification<T> var1);
     *              //根据条件查询所有
     *              List<T> findAll(@Nullable Specification<T> var1);
     *              //根据条件查询并且进行分页
     *              Page<T> findAll(@Nullable Specification<T> var1, Pageable var2);
     *              //根据查询条件，并且排序
     *              List<T> findAll(@Nullable Specification<T> var1, Sort var2);
     *
     *              long count(@Nullable Specification<T> var1);
     *
     *
     *                @Nullable
     *                 Predicate toPredicate(Root<T> var1, CriteriaQuery<?> var2, CriteriaBuilder var3);
     *                 root :根属性（查询所需要的属性
     *                 CriteriaQuery :自定义查询
     *                 CriteriaBuiler：查询构造器，封装了（like = )
     *                 ）
     */




    @Test
    public void testJpql() {
        List<Resume> byJpql = resumeDao.findByJpql(1,"test");
        for (int i = 0; i < byJpql.size(); i++) {
            System.out.println(byJpql.get(i));
        }
    }
  @Test
    public void testsql() {
        List<Resume> byJpql = resumeDao.findBySql("test%");
        for (int i = 0; i < byJpql.size(); i++) {
            System.out.println(byJpql.get(i));
        }
    }

    @Test
    public void testMethodName() {
        List<Resume> byJpql = resumeDao.findByNameLike("test%");
        for (int i = 0; i < byJpql.size(); i++) {
            System.out.println(byJpql.get(i));
        }
    }

    @Test
    public void testSpecification() {

        /**
         * 动态条件封装
         * 匿名内部类
         *
         * toPredicate: 动态组装查询条件
         * 借助于两个参数完成条件拼装 select * from tb_resume where name = ?
         * root : 获取需要查询的对象属性
         * criteriaBuilder:构件查询条件（模糊查询，精准查询）
         *
         */
        Specification<Resume> specification = new Specification<Resume>() {
            @Override
            public Predicate toPredicate(Root<Resume> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Object> name = root.get("name");
                //使用CriteriaBuilder 构建器
                Predicate test = criteriaBuilder.equal(name, "test");
                return test;
            }
        };
        Optional<Resume> one = resumeDao.findOne(specification);
        System.out.println(one.get());
    }


    @Test
    public void testSpecificationMulti() {

        Specification<Resume> specification = new Specification<Resume>() {
            @Override
            public Predicate toPredicate(Root<Resume> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Object> name = root.get("name");
                Path<Object> address = root.get("address");


                //使用CriteriaBuilder 构建器
                Predicate predicateEqual = criteriaBuilder.equal(name, "test");
                Predicate predicateLike = criteriaBuilder.like(address.as(String.class), "上%");

                Predicate and = criteriaBuilder.and(predicateEqual, predicateLike);

                return and;
            }
        };
        Optional<Resume> one = resumeDao.findOne(specification);
        System.out.println(one.get());
    }


    @Test
    public void testFindAllSqrt() {

        Sort idSort = new Sort(Sort.Direction.DESC, "id");

        List<Resume> all = resumeDao.findAll(idSort);

        for (int i = 0; i < all.size(); i++) {
            System.out.println(all.get(i));
        }
    }


    @Test
    public void testFindPage() {

//        PageRequest pageRequest1 = new PageRequest(2, 2);
        PageRequest pageRequest = PageRequest.of(2, 2);
        Page<Resume> resumePage = resumeDao.findAll(Pageable.unpaged());
        long totalElements = resumePage.getTotalElements();
        System.out.println(totalElements);
        List<Resume> content = resumePage.getContent();
        for (Resume resume : content) {
            System.out.println(resume);
        }

    }

}
