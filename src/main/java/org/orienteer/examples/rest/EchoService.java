package org.orienteer.examples.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/echo")
public class EchoService {
	 
	@GET
	@Path("/{param}")
	public String getMsg(@PathParam("param") String msg) {
 
		return msg;
 
	}
}
