package org.example.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.reggie.domain.dto.EmployeeDTO;
import org.example.reggie.domain.po.Employee;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 员工业务逻辑接口
 */
public interface EmployeeService extends IService<Employee> {

    /**
     * 员工登录
     * @return
     */
    Employee employeeServiceLogin(EmployeeDTO employeeDTO);

    /**
     * 员工分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    Page<Employee> employeeServicePage(Integer page,Integer pageSize,String name);

    /**
     * 根据id修改用户
     * @param employeeDTO
     */
    void updateServiceEmployee(EmployeeDTO employeeDTO);

    /**
     * 员工数据添加
     * @param employeeDTO
     * @return
     */
    void saveServiceEmployee(EmployeeDTO employeeDTO);
}
