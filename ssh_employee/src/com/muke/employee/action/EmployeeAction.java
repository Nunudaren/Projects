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

	// ע��ҵ�����
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
		// ����ҵ������
		Employee existEmployee = employeeService.login(employee);
		if (existEmployee == null) {
			// ��½ʧ��
			this.addActionError("�û������������");
			return INPUT; // ��ת��һ��ҳ��ȥ������һ��INPUT��ͼ
		} else {
			// ��½�ɹ�
			ActionContext.getContext().getSession()
					.put("existEmployee", existEmployee); // �����ѯ���Ķ�������֣�
			return SUCCESS;
		}
	}

	/**
	 * ��ҳ��ѯԱ���ķ���
	 */
	public String findAll() {
		PageBean<Employee> pageBean = employeeService.findByPage(currPage);
		//��PageBean����浽ֵջ
		ActionContext.getContext().getValueStack().push(pageBean);
		return "findAll";
	}

	/**
	 * ��ת�����Ա��ҳ��ִ�еķ���
	 */
	public String saveUI(){
		//��ѯ���в���
		List<Department> list = departmentService.findAll();
		ActionContext.getContext().getValueStack().set("list", list);
		return "saveUI";
	}
	
	/**
	 * ����Ա����ִ�з���
	 */
	public String save(){
		employeeService.save(employee);
		return "saveSuccess";
	}
	
	/**
	 * �༭Ա����ִ�з���
	 */
	public String edit(){
		//�ȸ���Ա����id��ѯԱ��
		employee = employeeService.findById(employee.getEid());
		//��ѯ���еĲ���
		List<Department> list = departmentService.findAll();
		ActionContext.getContext().getValueStack().set("list", list);
		return "editSuccess";
	}
	
	/**
	 * �޸�Ա����ִ�з���
	 */
	public String update(){
		employeeService.update(employee);
		return "updateSuccess";
	}
	
	/**
	 * ɾ��Ա����ִ�з���
	 */
	public String delete(){
		employee = employeeService.findById(employee.getEid());
		employeeService.delete(employee);
		return "deleteSuccess";
	}
	
	
}
