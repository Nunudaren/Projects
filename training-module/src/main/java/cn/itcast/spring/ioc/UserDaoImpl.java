package cn.itcast.spring.ioc;

public class UserDaoImpl {
	public UserDaoImpl(){
		System.out.println("init.....");
	}
	//要想让我运行，必须给我提供一个字符串
	//1.声明私有变量
	private String name;
	private Integer age;
	//2.提供该变量对应的set方法
	public void setName(String name) {
		this.name = name;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	//3.配置文件中添加依赖关系
	public void add(){
		System.out.println("dao.add...."+name+","+age);
	}
}
