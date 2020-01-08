package com.baizhi.ql;

import com.baizhi.ql.dao.CounterDao;
import com.baizhi.ql.entity.Counter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@SpringBootTest(classes = CmfzApplication.class)
@RunWith(value = SpringRunner.class)
public class TestCounter {
    @Autowired
    CounterDao counterDao;
    @Test
    public void testCounterSelect(){
        Counter counter = new Counter();
        counter.setUserId("1");
        Example example = new Example(Counter.class);
        example.and().andEqualTo("userId", "1");
        List<Counter> select = counterDao.selectByExample(example);
        System.out.println(select);
    }
}
