package com.howtodoinjava.web;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;


@Path("/customers")
@Consumes(value= MediaType.APPLICATION_JSON)
@Produces(value = MediaType.APPLICATION_JSON)
public class CustomerService {
	
	//Customer database pre-initialization
	private static final List<Customer> customers = new ArrayList<>();
	
	static {
		customers.add(new Customer(1L, "oscar", "1234"));
		customers.add(new Customer(2L, "juan", "1234"));
		customers.add(new Customer(3L, "maria", "1234"));
	}
	
	
	@GET
	public Response findAllCustomers() {
		return Response.ok(this.customers).build();
	}
	
	@POST
	public Response createCustomer(Customer customerRequest) {
		customerRequest.setId(customers.size()+1l);
		this.customers.add(customerRequest);
		return Response.ok(customerRequest).build();
	}
	
	@PUT
	public Response updateCustomer(Customer customerRequest) {
		List<Customer> found = this.customers.stream().filter(x -> customerRequest.getId() == x.getId()).collect(Collectors.toList());
		
		//Throws error in case of the customer not found
		if(found.isEmpty()) return Response.status(Status.BAD_REQUEST).entity("Customer not found").build();
		
		Customer updateCustomer = found.get(0);
		updateCustomer.setPassword(customerRequest.getPassword());
		updateCustomer.setCustomername(customerRequest.getCustomername());
		return Response.ok(updateCustomer).build();
	}
	
	@DELETE
	@Path("{customerId}")
	public Response deleteCustomer( @PathParam("customerId") long customerId) {
		System.out.println("customerId ==> " + customerId);
		List<Customer> found = this.customers.stream().filter(x -> customerId == x.getId().longValue()).collect(Collectors.toList());
		
		//Throws error in case of the customer not found
		if(found.isEmpty()) return Response.status(Status.BAD_REQUEST).entity("Customer not found").build();
		
		Customer updateCustomer = found.get(0);
		this.customers.remove(updateCustomer);
		return Response.noContent().build();
	}
	
	
	@HEAD
	public Response pingCustomersService() {
		return Response.noContent().header("running", true).build();
	}
	
}
