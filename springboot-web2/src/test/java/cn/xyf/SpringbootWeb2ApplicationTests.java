package cn.xyf;

import cn.xyf.config.MyMvcConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.servlet.LocaleResolver;

@SpringBootTest
class SpringbootWeb2ApplicationTests {

    @Test
    void contextLoads() {
//        ClassPathXmlApplicationContext
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyMvcConfig.class);
        LocaleResolver myLocaleResolver = context.getBean("newLocaleResolver", LocaleResolver.class);
        myLocaleResolver.resolveLocale(null);

//        DepartmentDao departmentDao = context.getBean("departmentDao", DepartmentDao.class);
//        Collection<Department> allDepartments = departmentDao.getAllDepartments();
//        for (Department department : allDepartments) {
//            System.out.println(department);
//        }

//        EmployeeService employeeService = context.getBean(EmployeeService.class);
//        Collection<Employee> employees = employeeService.select();
//        for (Employee employee : employees) {
//            System.out.println(employee);
//        }
    }

}
