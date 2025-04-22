package org.example.reggie.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import lombok.extern.slf4j.Slf4j;
import org.example.reggie.common.ResultStatus;
import org.example.reggie.domain.dto.EmployeeDTO;
import org.example.reggie.domain.po.Employee;
import org.example.reggie.exceptions.BusinessException;
import org.example.reggie.exceptions.SystemException;
import org.example.reggie.mapper.EmployeeMapper;
import org.example.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 唐三
 * description: 员工业务逻辑处理层
 */
@Slf4j
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee>
                        implements EmployeeService {

    @Override
    public Employee employeeServiceLogin(EmployeeDTO employeeDTO) {
        log.info("员工业务逻辑层触发 方法: {}","employeeServiceLogin(EmployeeDTO employeeDTO)");
        // 1,获取员工密码进行mp5加密
        String password = DigestUtil.md5Hex(employeeDTO.getPassword());
        System.out.println(password);
        // 2,查询数据库判断库中是否存在该员工,操作employee表
        Employee employee = baseMapper.selectOne(
                new LambdaUpdateWrapper<Employee>().
                        eq(Employee::getUsername,employeeDTO.getUsername())
        );
        // 3,没查到,抛出业务逻辑异常
        if (ObjectUtil.isEmpty(employee)) {
            throw new BusinessException(
                    "抱歉您的数据有误😭😭",
                    ResultStatus.USER_NAME_ERROR
            );
        }
        // 4,判断密码是否正确
        if (!ObjectUtil.equals(password,employee.getPassword())) {
            throw new BusinessException(
                    "抱歉您的密码错😡😡"
                    ,ResultStatus.USER_PASSWORD_ERROR
            );
        }
        // 5,判断用户是否禁用
        if (ObjectUtil.equals(0,employeeDTO.getStatus())) {
            throw new BusinessException(
                    "抱歉您已被禁用😼😼",
                    ResultStatus.COLL_STATUS_ERROR
            );
        }
        return employee;
    }

    @Override
    public Page<Employee> employeeServicePage(Integer page, Integer pageSize, String name) {
        log.info("员工业务逻辑层触发 方法: {}","employeeServicePage(Integer page, Integer pageSize, String name)");
        // 1,先分页
        Page<Employee> employeePage = new Page<>(page,pageSize);
        baseMapper.selectPage(
                employeePage,
                new LambdaQueryWrapper<Employee>().
                        like(ObjectUtil.isNotNull(name),Employee::getName,name).
                        orderByDesc(Employee::getCreateTime)
        );

        // 2,数据查询
        return employeePage;
    }

    @Override
    public void updateServiceEmployee(EmployeeDTO employeeDTO) {
        log.info("员工业务逻辑层触发 方法: {}","updateServiceEmployee(EmployeeDTO employeeDTO)");
        // 1,查找所有员工
        List<Employee> employees = Db.list(Employee.class);

        // 遍历
        employees.forEach(item -> {
            //不包括自己自身
            if (ObjectUtil.notEqual(item.getId(),employeeDTO.getId())) {
                //判断两个身份证是否重复
                if (ObjectUtil.equals(item.getIdNumber(),
                        employeeDTO.getIdNumber())) {

                    throw new BusinessException(
                            "抱歉您的身份证有误😡"
                            ,ResultStatus.USER_NUMBER_ERROR
                    );
                }

                //判断手机号码是否重复
                if (ObjectUtil.equals(item.getPhone(),
                        employeeDTO.getPhone())) {

                    throw new BusinessException(
                            "抱歉您的手机号有误😡"
                            ,ResultStatus.PHONE_ERROR
                    );
                }
            }
        });

        // id修改数据
        //数据转换
        Employee employee = BeanUtil.copyProperties(employeeDTO,Employee.class);
        System.out.println("emp:" + employee);

        try {
            Db.updateById(
                    BeanUtil.copyProperties(employeeDTO,Employee.class)
            );
        } catch (Exception ex) {
            throw new SystemException(
                    "服务器内部出现了问题😭",
                    ResultStatus.ENGINE_ERROR
            );
        }

    }

    @Override
    public void saveServiceEmployee(EmployeeDTO employeeDTO) {
        log.info("员工业务逻辑层触发 方法: {}","saveServiceEmployee(EmployeeDTO employeeDTO)");

        //随机密码生成
        String passwd = DigestUtil.md5Hex("111111");
        employeeDTO.setPassword(passwd);

        // 数据插入
        try {
            Db.save(
                    BeanUtil.copyProperties(employeeDTO,Employee.class)
            );
        } catch (Exception ex) {
            throw new SystemException(
                    "抱歉您添加失败😭😭",
                    ResultStatus.USER_NAME_ERROR
            );
        }
    }
}
