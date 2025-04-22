package org.example.reggie.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.example.reggie.common.ResultViewEntity;
import org.example.reggie.domain.dto.EmployeeDTO;
import org.example.reggie.domain.po.Employee;
import org.example.reggie.domain.vo.EmployeeVO;
import org.example.reggie.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author 唐三
 * description: 员工JSON返回表示层,用于返回给页面json
 */
@Api("员工JSON返回表示层")
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @ApiOperation("员工登录业务接口")
    @PostMapping("/login")
    public ResultViewEntity<EmployeeVO> employeeLogin(
            @ApiParam("员工实体") @RequestBody EmployeeDTO employeeDTO,
            HttpSession session
    ) {

        log.info("接口: {} ,请求类型: {} ,employee: {}","/employee/login","POST",employeeDTO);

        Employee employee = employeeService.employeeServiceLogin(employeeDTO);
        // 6,员工id session封装
        session.setAttribute("employee",employee.getId());
        return ResultViewEntity.successful(BeanUtil.copyProperties(employee,EmployeeVO.class));
    }

    @ApiOperation("登录退出接口")
    @PostMapping("/logout")
    public ResultViewEntity<String> employeeLogin(HttpSession session) {

        log.info("退出登录");
        //清除session
        session.removeAttribute("employee");
        return ResultViewEntity.successful("恭喜您成功退出😊");
    }

    @GetMapping("/page")
    public ResultViewEntity<Page<EmployeeVO>> pageEmployee(
            @ApiParam("页数") @RequestParam Integer page,
            @ApiParam("每页条数") @RequestParam Integer pageSize,
            @ApiParam("搜索") @RequestParam(required = false)  String name
     ) {

        log.info("请求格式: {} ,地址: {}","GET","/employee/page");
        log.info("页面:" + page + " ,条数:" + pageSize + " ,名称:" + name);

        //获取分页
        Page<Employee> employeePage = employeeService.employeeServicePage(page,pageSize,name);
        //po转vo
        Page<EmployeeVO> employeeVOPage = new Page<>();
        BeanUtil.copyProperties(employeePage,employeeVOPage);

        return ResultViewEntity.successful(employeeVOPage);
    }


    @ApiOperation("员工数据回调接口")
    @GetMapping("/{id}")
    public ResultViewEntity<EmployeeVO> awareEmployee(
            @ApiParam("地址类型参数") @PathVariable String id
    ) {

        log.info("请求格式: {} ,地址: {}","GET","/employee/{id}");

        EmployeeVO employeeVO = BeanUtil.copyProperties(employeeService.getById(id),EmployeeVO.class);

        return ResultViewEntity.successful(employeeVO);
    }

    @ApiOperation("修改员工接口")
    @PutMapping
    public ResultViewEntity<String> updateEmployee(
            @ApiParam("员工实体") @RequestBody EmployeeDTO employeeDTO
    ) {

        log.info("请求格式: {} ,地址: {}","PUT","/employee/");
        log.info("参数employeeDTO: {}",employeeDTO);

        employeeService.updateServiceEmployee(employeeDTO);

        return ResultViewEntity.successful("成功修改😁😁");
    }

    @ApiOperation("成员添加接口")
    @PostMapping
    public ResultViewEntity<String> saveEmployee(
            @ApiParam("员工实体") @RequestBody EmployeeDTO employeeDTO
    ) {

        log.info("请求格式: {} ,地址: {}","POST","/employee/");
        log.info("数据成功添加");

        employeeService.saveServiceEmployee(employeeDTO);

        return ResultViewEntity.successful("成功添加😁😁");
    }
}
