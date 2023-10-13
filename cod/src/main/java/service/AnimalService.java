package service;

import dao.AnimalDAO;
import model.Animal;
import spark.Request;
import spark.Response;
import com.google.gson.Gson;

import java.util.List;

public class AnimalService {

    private AnimalDAO animalDAO = new AnimalDAO();

    public String insertAnimal(Request request, Response response) {
        String nome = request.queryParams("nomeInput");
        String raca = request.queryParams("racaInput");
        String descricao = request.queryParams("descricaoInput");
        String caracteristica = request.queryParams("caracteristicaInput");
        String tamanho = request.queryParams("tamanhoInput");
        String especie = request.queryParams("especieInput");
        String estado = request.queryParams("estadoInput");
        String cidade = request.queryParams("cidadeInput");
        String fotoURL = request.queryParams("fotoInput");

        if (nome == null || nome.isEmpty() || raca == null || raca.isEmpty() ||
                descricao == null || descricao.isEmpty() || caracteristica == null || caracteristica.isEmpty() ||
                tamanho == null || tamanho.isEmpty() || especie == null || especie.isEmpty() ||
                estado == null || estado.isEmpty() || cidade == null || cidade.isEmpty() || fotoURL == null || fotoURL.isEmpty()) {
            response.status(400);
            return "Preencha todos os campos obrigatórios.";
        }

        Animal novoAnimal = new Animal(nome, raca, null, descricao, especie, cidade, estado, fotoURL, caracteristica, tamanho);
        boolean insercaoSucesso = animalDAO.insert(novoAnimal);

        if (insercaoSucesso) {
            return "Inserção bem-sucedida.";
        } else {
            response.status(500);
            return "A inserção falhou.";
        }
    }

    public String getAllAnimais(Request request, Response response) {
        List<Animal> animais = animalDAO.getAll();
        Gson gson = new Gson();
        response.type("application/json");
        return gson.toJson(animais);
    }

    public String getAnimalToUpdate(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Animal animalParaAtualizar = animalDAO.get(id);

        if (animalParaAtualizar != null) {
            Gson gson = new Gson();
            response.type("application/json");
            return gson.toJson(animalParaAtualizar);
        } else {
            response.status(404);
            return "Animal com ID " + id + " não encontrado para atualização.";
        }
    }

    public boolean deleteAnimal(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        boolean exclusaoSucesso = animalDAO.delete(id);

        if (exclusaoSucesso) {
            return true;
        } else {
            response.status(500);
            return false;
        }
    }

    public String getAnimalById(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Animal animalEncontrado = animalDAO.get(id);

        if (animalEncontrado != null) {
            Gson gson = new Gson();
            response.type("application/json");
            return gson.toJson(animalEncontrado);
        } else {
            response.status(404);
            return "Animal com ID " + id + " não encontrado.";
        }
    }

    public String getRandomAnimals(Request request, Response response) {
        int numberOfAnimals = 6;

        List<Animal> animaisAleatorios = animalDAO.getRandomAnimals(numberOfAnimals);

        if (animaisAleatorios != null && !animaisAleatorios.isEmpty()) {
            Gson gson = new Gson();
            response.type("application/json");
            return gson.toJson(animaisAleatorios);
        } else {
            response.status(404);
            return "Não há animais suficientes no banco de dados.";
        }
    }
    
    //Codigo para a pagina Animais onde sera mostrado um numero maior de animais
    public String getRandomAnimalsPagAdotar(Request request, Response response) {
        int numberOfAnimals = 20;

        List<Animal> animaisAleatorios = animalDAO.getRandomAnimals(numberOfAnimals);

        if (animaisAleatorios != null && !animaisAleatorios.isEmpty()) {
            Gson gson = new Gson();
            response.type("application/json");
            return gson.toJson(animaisAleatorios);
        } else {
            response.status(404);
            return "Não há animais suficientes no banco de dados.";
        }
    }
}
