package com.codigo.sql.app;

import static spark.Spark.*;

import com.codigo.sql.service.LoginService;

public class Aplicacao {
	
	private static LoginService loginService = new LoginService();
	
    public static void main(String[] args) {
        port(4567);
        
        staticFiles.location("/public");
        
        post("/index.html/insert", (request, response) -> loginService.insert(request, response));

        get("/index.html/:id", (request, response) -> loginService.get(request, response));
        
        get("/index.html/list/:orderby", (request, response) -> loginService.getAll(request, response));

        get("/index.html/update/:id", (request, response) -> loginService.getToUpdate(request, response));
        
        post("/index.html/update/:id", (request, response) -> loginService.update(request, response));
           
        get("/index.html/delete/:id", (request, response) -> loginService.delete(request, response));

             
    }
}
