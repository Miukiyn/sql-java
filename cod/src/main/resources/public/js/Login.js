//criação de variaveis globais vazias
let usuarios = [];
let loggedUser = {}

let userInput = document.getElementById("userInput");
let passwordInput = document.getElementById("passwordInput");

if (sessionStorage.getItem('loggedUser') == true) {
    loggedUser = JSON.parse(sessionStorage.getItem('loggedUser'));
}

if (sessionStorage.getItem('dbUsuarios') == null) {
    usuarios.push({id: '0'});
    usuarios.push({id: '1', type: 'Administrador', user: 'admin', email: 'admin@teste.com.br', password: '123456', city: 'BELO HORIZONTE'});
    sessionStorage.setItem('dbUsuarios', JSON.stringify(usuarios));
}

else{
    console.log("Usuarios carregados do SESSION STORAGE com sucesso!")
    usuarios = JSON.parse(sessionStorage.getItem('dbUsuarios'));
}

function addUser() {
    console.log(usuarios);
    let user = document.getElementById("userInput").value;
    let password = document.getElementById("emailInput").value;
    let email = document.getElementById("passwordInput").value;
    let city = document.getElementById("addressInput").value;
    let newUser = {"id": usuarios.length + 1, "type": "ong", "user": user, "email": email, "password": password, "city": city};
    usuarios.push(newUser);
    sessionStorage.setItem('dbUsuarios', JSON.stringify(usuarios));
}

function loginUser(user, password) {
    user = document.getElementById(user).value;
    password = document.getElementById(password).value;
    
    for (let i = 0; i < usuarios.length; i++) {        
        if (usuarios[i].user == user && usuarios[i].password == password) {
            loggedUser = {}
            loggedUser.id = usuarios[i].id;
            loggedUser.user = usuarios[i].user;
            loggedUser.email = usuarios[i].email;
            loggedUser.password = usuarios[i].password;
            sessionStorage.setItem('loggedUser', JSON.stringify(loggedUser));
            window.location.href = "./user_page.html";
        }
    }

    if(loggedUser.id == undefined){
        alert("Usuário ou Senha incorretos");
        console.log(usuarios[0]);
    }
}