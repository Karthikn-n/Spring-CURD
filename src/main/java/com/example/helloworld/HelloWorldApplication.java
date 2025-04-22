package com.example.helloworld;

import com.example.helloworld.config.AppConfig;
import com.example.helloworld.impl.Airtel;
import com.example.helloworld.impl.Jio;
import com.example.helloworld.interfaces.Sim;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class HelloWorldApplication {
	/// we can define the container here to use the manage the beans using that container
	/// We have two type of Containers in Spring
	/// 1) BeanFactoryContainer
	/// 2) ApplicationContextContainer (Advanced one built top on BeanFactory container)
	/// The Job of the contructor is
	/// 	Creating Beans
	/// 	Injecting dependencies
	/// 	Managing its lifecycles
	/// 	Reading XML configurations
	public static void main(String[] args) {
		 SpringApplication.run(HelloWorldApplication.class, args);
		/// It will create a Spring container IoC where all the spring configuration
		///  in the AppConfig class and find its beans using ApplicationContext
//		ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
//		/// It will get the bean name called sim if no beans is named as sim it will look the method name sim
//		Sim sim = ctx.getBean("sim", Sim.class);
//		Sim sim2 = ctx.getBean("jio", Sim.class);
//		sim2.calling();
//		sim2.data();
	}
	/// Important notes
	/// There are classes extends the ApplicationContext Container
	/// 1) AnnotationConfigApplicationContext container
	/// 2) AnnotationConfigWebApplicationContext
	/// 3) XmlWebApplicationContext
	/// 4) FileSystemXmlApplicationContext
	/// 5) ClassPathXmlApplicationContext
}
