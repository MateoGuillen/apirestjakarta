package com.howtodoinjava.web;

import java.util.HashMap;
import java.util.Map;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.MediaType;


@Path("/hello")
public class hello {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response hellow() {
	  Map<String, String> response = new HashMap<>();
      response.put("message", "helloworld");
	  return Response.ok(response).build();
  }
}
