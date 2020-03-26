package cn.xyf;

import cn.xyf.config.Column;
import cn.xyf.config.Table;
import cn.xyf.pojo.Blog;
import cn.xyf.service.JucBlogServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.LongStream;


@SpringBootApplication
@MapperScan("cn.xyf.mapper")
public class JucDemoApplication {

    public static void main(String[] args) throws ClassNotFoundException {
        ConfigurableApplicationContext context = SpringApplication.run(JucDemoApplication.class, args);

//        test(context);
        long start = System.currentTimeMillis();
        long sum = LongStream.rangeClosed(0, 1_000_000_000).parallel().reduce(0, Long::sum);
        System.out.println("sum="+sum+",time="+(System.currentTimeMillis()-start));
    }

    public static void test2() throws ClassNotFoundException {

        Class cls = Class.forName("cn.xyf.pojo.Student");

        Table table = (Table) cls.getAnnotation(Table.class);
        System.out.println("table.name="+table.name());


        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            Column col = field.getAnnotation(Column.class);
            System.out.println("col.name="+col.name()
                    +",col.type="+col.type()
                    +",col.length="+col.length());
        }
    }

    public static void test(ConfigurableApplicationContext context) {
        ThreadPoolTaskExecutor executor = context.getBean("taskConcurrentExecutor", ThreadPoolTaskExecutor.class);
        System.out.println(executor.getThreadNamePrefix());

        JucBlogServiceImpl jucBlogService = context.getBean("jucBlogServiceImpl", JucBlogServiceImpl.class);

        long start = 0;
        long end = 0;

        System.out.println("******************");

        start = System.currentTimeMillis();
        List<Blog> blogs = jucBlogService.selectAll();
        for (Blog blog : blogs) {
            System.out.println(blog);
        }
        end = System.currentTimeMillis();
        System.out.println(end-start);

        executor.shutdown();
    }

}
