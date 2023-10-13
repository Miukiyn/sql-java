package app;

import static spark.Spark.*;


import service.UsuarioService;
import service.ONGService;
import service.AnimalService;
import service.LoginService;




public class Aplicacao {

    private static UsuarioService usuarioService = new UsuarioService();
    private static ONGService ongService = new ONGService(); 
    private static AnimalService animalService = new AnimalService();
    private static LoginService loginService = new LoginService();


    public static void main(String[] args) {
        port(6789);

        staticFiles.location("/public");

        // Metodo para registrar usuario
        post("/usuario/insert", (request, response) -> usuarioService.insertUsuario(request, response));

        
        // Metodo para registrar Ong
        post("/ong/insert", (request, response) -> ongService.insertONG(request, response));     
        
        
        // Metodo para inserir animal
        post("/animal/insert", (request, response) -> loginService.insertAnimal(request, response));
        
        
        // Metodos de login usados para recuperar dados do usuario/ong logado
        post("/usuario/login", (request, response) -> loginService.login(request, response));
        
        post("/ong/login", (request, response) -> loginService.login(request, response));
        
        get("/alguma-rota", (request, response) -> loginService.nomeUserOrONG(request, response));
        
        // Mostrar animais
        get("/animais", (request, response) -> animalService.getRandomAnimals(request, response));
        
        get("/animaisPaginaDedicada", (request, response) -> animalService.getRandomAnimalsPagAdotar(request, response));

        
               
    }
}


