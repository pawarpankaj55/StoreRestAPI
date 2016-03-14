package com.controller;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bean.Store;
import com.bean.StoreList;
import com.dao.StoreDAO;

@Controller
public class StoreController {

	
	private Jaxb2Marshaller jaxb2Mashaller;
	
	public void setJaxb2Mashaller(Jaxb2Marshaller jaxb2Mashaller) {
		this.jaxb2Mashaller = jaxb2Mashaller;
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/createStore")
	public @ResponseBody String  addStore(@RequestBody String body) {
		Source source = new StreamSource(new StringReader(body));
		Store newStore = (Store) jaxb2Mashaller.unmarshal(source);
	    int result = StoreDAO.addStore(newStore);	
		String response = "Operation not successful";
	    if(result != 0){
	    	response = "Store Added Successfully";
	    }
		return response; 
	}
	
   @RequestMapping(method=RequestMethod.PUT, value="/updateStore")
	public @ResponseBody String updateStore(@RequestBody String body) {
		Source source = new StreamSource(new StringReader(body));
		Store newStore = (Store) jaxb2Mashaller.unmarshal(source);
		int result = StoreDAO.updateStore(newStore);
		String response = "Operation not successful";
	    if(result != 0){
	    	response = "Store Updated Successfully";
	    }
		return response; 
	}

	
	@RequestMapping(method=RequestMethod.DELETE, value="/deleteStore/{id}")
	public @ResponseBody String removeStore(@PathVariable String id) {
		int result = StoreDAO.deleteStore(Integer.parseInt(id));
		String response = "Operation not successful";
	    if(result != 0){
	    	response = "Store Deleted Successfully";
	    }
		return response;
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/locateStore/{lat}/{lng}/{dist}")
	public @ResponseBody String locateStore(@PathVariable String lat,@PathVariable String lng,@PathVariable String dist) {
		List<Store> storeList = StoreDAO.locateStore(Double.parseDouble(lat),Double.parseDouble(lng),Integer.parseInt(dist));
		StoreList list = new StoreList();
		list.setStores(storeList);
		StringWriter out = new StringWriter();
		jaxb2Mashaller.marshal(list, new StreamResult(out));
		return out.toString();
	}
}
