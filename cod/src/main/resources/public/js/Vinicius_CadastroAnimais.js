//CADASTRO DE ANIMAIS
//Verifica se existe a classe criada no DB, caso não exista, cria uma vazia como referência
function leDados() {
    let strDados = sessionStorage.getItem('dbAnimal');
    let objDados = {};
    if (strDados) {
        objDados = JSON.parse(strDados);
    }
    else {
        objDados = {
            animais: [
                { nome: "Belinha", tipo: "Cachorro", raca: "Poodle", idade: "8", tamanho: "médio", descricao: "Bagunceira", ongID: "1" }
            ]
        }
    }

    return objDados;
}
//Salva os dados em sessionStorage
function salvaDados(dados) {
    sessionStorage.setItem('dbAnimal', JSON.stringify(dados));
}
//Função para verificar se existe algum campo vazio
function verificaVazio(a, b, c, d, e) {
    a = a.trim();
    b = b.trim();
    c = c.trim();
    d = d.trim();
    e = e.trim();
    if (a == null || a == "") {
        return true;
    }
    if (b == null || b == "") {
        return true;
    }
    if (c == null || c == "") {
        return true;
    }
    if (d == null || d == "") {
        return true;
    }
    if (e == null || e == "") {
        return true;
    }
}
//Limpa os espaços adicionais, verifica se possui campo vazio, salva os dados
function adicionaDados() {
    let objDados = leDados();
    let strNome = document.getElementById('nome').value;
    let strRaca = document.getElementById('raca').value;
    let strTipo = document.getElementById('tipoAnimal').value;
    let strIdade = document.getElementById('idade').value;
    let strTamanho = document.getElementById('tamanho').value;
    let strDescricao = document.getElementById('descricao').value;
    if (verificaVazio(strNome, strRaca, strIdade, strTamanho, strDescricao)) {
        alert("Por favor, preencha todos os campos");
    }
    else {
        let novoAnimal = {
            nome: strNome,
            tipo: strTipo,
            raca: strRaca,
            idade: strIdade,
            tamanho: strTamanho,
            descricao: strDescricao,
            ongID: loggedUser.id
        };
        objDados.animais.push(novoAnimal);
        salvaDados(objDados);
        document.getElementById('nome').value = "";
        document.getElementById('raca').value = "";
        document.getElementById('idade').value = "";
        document.getElementById('tamanho').value = "";
        document.getElementById('descricao').value = "";
        alert(`Animal ${strNome} cadastrado`);
    }
    printDados();
}
//Verifica se o DB possui algum dado
function verificaDados() {
    let objDados = leDados();
    console.log(objDados);
    if (objDados.animais.length == 1) {
        alert('Não existe nenhum animal cadastrado');
        //window.location.href = 'index.html';
    }
    else {
        //window.location.href = 'editar.html';
    }
}
//Exibe os dados que existem no DB
function printDados() {
    let main = document.getElementById('animalTable');
    let strHTML = '';
    let objDados = leDados();
    strHTML += `<tr>
        <th>Nome</th>
        <th>Raça</th>
        <th>Tipo</th>
        <th>Idade</th>
        <th>Tamanho</th>
        <th>Descrição</th>
    </tr>`
    for (let i = 0; i < objDados.animais.length; i++) {
        if(objDados.animais[i].ongID == loggedUser.id){
            strHTML += `<tr>
            <td>${objDados.animais[i].nome}
            </td>
            <td>${objDados.animais[i].raca}</td>
            <td>${objDados.animais[i].tipo}</td>
            <td>${objDados.animais[i].idade}</td>
            <td>${objDados.animais[i].tamanho}</td>
            <td>${objDados.animais[i].descricao}</td>
            <td><form>
            
            <button onclick="excluiDados(${i})" class="excluirDados">Excluir Animal</button></p></td>
            </tr>
            `
            /*<p><button id="editarDados type="submit" name="id" value="${i}">Editar Animal</button>
            </form>*/
        }        
    }
    main.innerHTML = strHTML;
}

//Exclui o Animal com ID desejado
function excluiDados(id) {

    let objDados = leDados();
    alert(`Animal: ${objDados.animais[id].nome} removido`);
    for (let i = 1; i < objDados.animais.length; i++) {
        if (i == id) {
            objDados.animais.splice(i, 1);
        }
    }
    salvaDados(objDados);
    verificaDados();

}
//Formulário para edição do animal selecionado
function editaDados(id) {
    let objDados = leDados();
    let main = document.getElementById('container main_modificar');
    let strHTML = '';
    strHTML = ` 
    <section class="row qualAnimal">
        <div class="col-12">
            Editando o Animal: <b>${objDados.animais[id].nome}</b>
    </div>
    </section>
    <section class="row formulario">
    <div class="col-12">
        <p>
            <label for="nome">Nome:</label>
        </p>
        <p>
            <input type="text" id="nome" name="nnome" autocomplete="off" />
        </p>
    </div>
    <div class="col-12">
                <p>
                    <label for="tipoAnimal">Escolha qual o animal:</label>
                </p>
                <p>
                <select name="tipoAnimal" id="tipoAnimal">
                    <option value="Cachorro">Cachorro</option>
                    <option value="Gato">Gato</option>
                    <option value="Pássaro">Pássaro</option>
                  </select>
                </p>
    </div>
    <div class="col-12">
        <p>
            <label for="raca">Raça:</label>
        </p>
        <p>
            <input type="text" id="raca" name="nraca" autocomplete="off" />
        </p>
    </div>
    <div class="col-12">
        <p>
            <label for="idade">Idade (Anos):</label>
        </p>
        </p>
        <p>
            <input type="text" id="idade" name="nidade" autocomplete="off"/>
        </p>
    </div>
    <div class="col-12">
        <p>
            <label for="tamanho">Tamanho:</label>
        </p>
        <p>
            <select name="tamanho" id="tamanho">
            <option value="Pequeno">Pequeno</option>
            <option value="Médio">Médio</option>
            <option value="Grande">Grande</option>
            </select>
        </p>
    </div>
    <div class="col-12">
        <p>
            <label for="descricao">Descrição:</label>
        </p>
        <p>
            <br>
            <textarea id="descricao" rows="5" cols="60" name="descricao"></textarea>
            <br>
        </p>
    </div>
    <p><button onclick="modificaDados(${id})" id="enviaDados" type="submit">Confirmar</button></p>`
    main.outerHTML = strHTML;

}
//Edita os dados
function modificaDados(id) {
    let objDados = leDados();
    let strNome = document.getElementById('nome').value;
    let strTipo = document.getElementById('tipoAnimal').value;
    let strRaca = document.getElementById('raca').value;
    let strIdade = document.getElementById('idade').value;
    let strTamanho = document.getElementById('tamanho').value;
    let strDescricao = document.getElementById('descricao').value;
    if (verificaVazio(strNome, strRaca, strIdade, strTamanho, strDescricao)) {
        alert("Por favor, preencha todos os campos");
    }
    else {
        objDados.animais[id].nome = strNome;
        objDados.animais[id].tipo = strTipo;
        objDados.animais[id].raca = strRaca;
        objDados.animais[id].idade = strIdade;
        objDados.animais[id].tamanho = strTamanho;
        objDados.animais[id].descricao = strDescricao;
        salvaDados(objDados);
        window.location.href = 'editar.html';

    }
}
//CADASTRO DE ANIMAIS

//---------------------------------------------------------------------------CADASTRO DE DICAS
function leDica() {
    let strDados = sessionStorage.getItem('dbDica');
    let objDica= {};
    if (strDados) {
        objDica = JSON.parse(strDados);
    }
    else {
        objDica = {
            dicas: [
                { titulo: "", texto: "" }
            ]
        }
    }

    return objDica;
}
function salvaDica(dados) {
    sessionStorage.setItem('dbDica', JSON.stringify(dados));
}
function adicionaDica() {
    let objDica = leDica();
    let strTitulo = document.getElementById('tDica').value;
    let strTexto = document.getElementById('dDica').value;
    if (verificaVazioDica(strTexto, strTitulo)) {
        alert("Por favor, preencha todos os campos");
    }
    else {
        let novaDica = {
            titulo: strTitulo,
            texto: strTexto
        };
        objDica.dicas.push(novaDica);
        salvaDica(objDica);
        document.getElementById('tDica').value = "";
        document.getElementById('dDica').value = "";
        alert(`Dica: ${strTitulo} cadastrada`);
    }

}
function verificaVazioDica(a, b) {
    a = a.trim();
    b = b.trim();
    if (a == null || a == "") {
        return true;
    }
    if (b == null || b == "") {
        return true;
    }
}
function verificaDica() {

    let objDica = leDica();
    if (objDica.dicas.length == 1) {
        alert('Não existe nenhuma dica cadastrada');
        window.location.href = 'dicas.html';
    }
    else {
        window.location.href = 'editarDica.html';
    }
}
function printDica() {
    let main = document.getElementById('teste');
    let strHTML = '';
    let objDica = leDica();
    for (let i = 1; i < objDica.dicas.length; i++) {
        strHTML = strHTML + `<section class="row mostrar"><div class="col-12"><p>Título: ${objDica.dicas[i].titulo}</p>
        <p>Texto: ${objDica.dicas[i].texto}</p>
    <section class="row btn_Dicas">
            <div class="col-12">
                <form action="modificarDica.html" method="get">
                <p><button id="editarDica type="submit" name="id" value="${i}">Editar Dica</button>
                </form>
            </div> 
            <div class="col-12">
                <button onclick="excluiDica(${i})" id="excluirDica" type="submit">Excluir Dica</button></p>
            </div>
    </section>`
    }
    main.outerHTML += strHTML;
}
function excluiDica(id) {

    let objDica = leDica();
    alert(`Dica: ${objDica.dicas[id].titulo} removido`);
    for (let i = 1; i < objDica.dicas.length; i++) {
        if (i == id) {
            objDica.dicas.splice(i, 1);
        }
    }
    salvaDica(objDica);
    verificaDica();
}
function editaDica(id) {

    let main = document.getElementById('container main_modificar');
    let strHTML = '';
    strHTML = ` <section class="row formulario">
    <div class="col-12">
        <p>
            <label for="tDica">Título</label>
        </p>
        <p>
            <input type="text" id="tDica" name="tDica" autocomplete="off" />
        </p>
    </div>
    <div class="col-12">
        <p>
            <label for="dDica">Texto:</label>
        </p>
        <p>
            <br>
            <textarea id="dDica" rows="5" cols="60" name="dDica"></textarea>
            <br>
        </p>
    </div>
    <p><button onclick="modificaDica(${id})" id="enviaDica" type="submit">Confirmar</button></p>`
    main.outerHTML = strHTML;

}

function modificaDica(id) {
    let objDica = leDica();
    let strTitulo = document.getElementById('tDica').value;
    let strTexto = document.getElementById('dDica').value;
    if (verificaVazioDica(strTexto, strTitulo)) {
        alert("Por favor, preencha todos os campos");
    }
    else {
        objDica.dicas[id].titulo = strTitulo;
        objDica.dicas[id].texto = strTexto;
        salvaDica(objDica);
        window.location.href = 'editarDica.html';

    }
}

printDados();