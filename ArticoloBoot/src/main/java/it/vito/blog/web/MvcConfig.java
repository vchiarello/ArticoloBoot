package it.vito.blog.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/index").setViewName("index");
		registry.addViewController("/getItem").setViewName("dettaglioItem");
		registry.addViewController("/items/partialsListItem").setViewName("items/partialsListItem::content");
		registry.addViewController("/items/partialsEditList").setViewName("items/editListItem::content");

		registry.addViewController("/items/partialsViewItem").setViewName("items/partialsViewItem::content");
		registry.addViewController("/items/partialsCreateItem").setViewName("items/partialsCreateItem::content");
		registry.addViewController("/items/partialsEditItem").setViewName("items/partialsEditItem::content");

		registry.addViewController("/items/partialsViewSlideShowItem").setViewName("items/partialsViewSlideShowItem::content");
		registry.addViewController("/items/partialsCreateSlideShowItem").setViewName("items/partialsCreateSlideShowItem::content");
		registry.addViewController("/items/partialsEditSlideShowItem").setViewName("items/partialsEditSlideShowItem::content");
		registry.addViewController("/items/login").setViewName("/items/login::content");
		registry.addViewController("/login").setViewName("/items/login::content");
		registry.addViewController("/templateAttendere").setViewName("/items/templateAttendere");
	}
	
}
