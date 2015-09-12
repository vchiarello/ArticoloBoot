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
		registry.addViewController("/items/partialsEditItem").setViewName("items/partialsEditItem::content");
		registry.addViewController("/items/partialsCreateItem").setViewName("items/partialsCreateItem::content");
		registry.addViewController("/items/partialsCreateItemw1").setViewName("items/partialsCreateItemw1::content");
		registry.addViewController("/items/partialsCreateItemw2").setViewName("items/partialsCreateItemw2::content");
		registry.addViewController("/items/partialsCreateItemw3").setViewName("items/partialsCreateItemw3::content");
		registry.addViewController("/vito").setViewName("vito");
		
		registry.addViewController("/hotels/partialsListVito").setViewName("hotels/partialsListVito::content");
		registry.addViewController("/hotels/partialsList").setViewName("hotels/partialsList::content");
		registry.addViewController("/hotels/partialsCreate").setViewName("hotels/partialsCreate::content");
		registry.addViewController("/hotels/partialsEdit").setViewName("hotels/partialsEdit::content");
		registry.addViewController("/mappings/partialsMappings").setViewName("mappings/partialsMappings::content");

	}
	
}
