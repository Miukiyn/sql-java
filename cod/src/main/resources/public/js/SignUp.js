//criação de variaveis globais vazias
let emailInput = document.getElementById("emailInput");
let addressInput = document.getElementById("addressInput");
let signUpButton = document.getElementById("signUpButton");

signUpButton.addEventListener("click", addUser);
emailInput.addEventListener("keyup", formIsValid);
passwordInput.addEventListener("keyup", formIsValid);
addressInput.addEventListener("click", formIsValid);

function formIsValid(){
    if( emailInput.value.length >= 8    &&
        passwordInput.value.length >= 8 &&
        addressInput.value.length > 0
    ){
        console.log("Button")
        signUpButton.removeAttribute("disabled");
    }

    else{
        signUpButton.setAttribute("disabled", "true");
    }
}

function addUser() {
    console.log(usuarios);
    let user = document.getElementById("userInput").value;
    let password = document.getElementById("passwordInput").value;
    let email = document.getElementById("emailInput").value;
    let city = document.getElementById("addressInput").value;
    let newUser = {"id": usuarios.length, "type": "ong", "user": user, "email": email, "password": password, "city": city};
    usuarios.push(newUser);
    sessionStorage.setItem('loggedUser', JSON.stringify(newUser));
    sessionStorage.setItem('dbUsuarios', JSON.stringify(usuarios));
    window.location.href = 'user_page.html';
}

function logoutUser() {    
    sessionStorage.setItem('loggedUser', JSON.stringify({}));
    //window.location.href = 'index.html'; (???)
}