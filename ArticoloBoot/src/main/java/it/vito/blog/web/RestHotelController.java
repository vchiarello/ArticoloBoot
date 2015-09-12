package it.vito.blog.web;

import java.util.LinkedList;
import java.util.List;

import mvctest.domain.Hotel;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/hotels")
public class RestHotelController {
	@RequestMapping(method=RequestMethod.GET)
	public List<Hotel> list() {
		Hotel h = new Hotel();
		h.setAddress("indirizzo 1");
		h.setId(1l);
		h.setName("Nome 1");
		h.setVersion(new Long(1));
		h.setZip("00100");
		List<Hotel> l = new LinkedList<Hotel>();
		l.add(h);
		return l;
	}

}
