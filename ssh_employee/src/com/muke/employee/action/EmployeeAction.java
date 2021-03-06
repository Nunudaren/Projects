package com.muke.employee.action;

import java.util.List;

import com.muke.employee.domain.Department;
import com.muke.employee.domain.Employee;
import com.muke.employee.domain.PageBean;
import com.muke.employee.service.DepartmentService;
import com.muke.employee.service.EmployeeService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class EmployeeAction extends ActionSupport implements
		ModelDriven<Employee> {

	private Employee employee = new Employee();

	@Override
	public Employee getModel() {
		return employee;
	}

	// 注入业务层类
	private EmployeeService employeeService;
	private DepartmentService departmentService;
	
	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}


	private Integer currPage = 1;

	public void setCurrPage(Integer currPage) {
		this.currPage = currPage;
	}

	public String login() {
		System.out.println("login..");
		// 调用业务层的类
		Employee existEmployee = employeeService.login(employee);
		if (existEmployee == null) {
			// 登陆失败
			this.addActionError("用户名或密码错误！");
			return INPUT; // 跳转到一个页面去，返回一个INPUT视图
		} else {
			// 登陆成功
			ActionContext.getContext().getSession()
					.put("existEmployee", existEmployee); // 存入查询到的对象和名字；
			return SUCCESS;
		}
	}

	/**
	 * 分页查询员工的方法
	 */
	public String findAll() {
		PageBean<Employee> pageBean = employeeService.findByPage(currPage);
		//将PageBean对象存到值栈
		ActionContext.getContext().getValueStack().push(pageBean);
		return "findAll";
	}

	/**
	 * 跳转到添加员工页面执行的方法
	 */
	public String saveUI(){
		//查询所有部门
		List<Department> list = departmentService.findAll();
		ActionContext.getContext().getValueStack().set("list", list);
		return "saveUI";
	}
	
	/**
	 * 保存员工的执行方法
	 */
	public String save(){
		employeeService.save(employee);
		return "saveSuccess";
	}
	
	/**
	 * 编辑员工的执行方法
	 */
	public String edit(){
		//先根据员工的id查询员工
		employee = employeeService.findById(employee.getEid());
		//查询所有的部门
		List<Department> list = departmentService.findAll();
		ActionContext.getContext().getValueStack().set("list", list);
		return "editSuccess";
	}
	
	/**
	 * 修改员工的执行方法
	 */
	public String update(){
		employeeService.update(employee);
		return "updateSuccess";
	}
	
	/**
	 * 删除员工的执行方法
	 */
	public String delete(){
		employee = employeeService.findById(employee.getEid());
		employeeService.delete(employee);
		return "deleteSuccess";
	}
	
	
}
