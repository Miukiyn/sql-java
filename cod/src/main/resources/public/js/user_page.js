let userOutput = document.getElementById("userOutput");
let emailOutput = document.getElementById("emailOutput");

let loggedUser = JSON.parse(sessionStorage.getItem("loggedUser"));
console.log(loggedUser);

userOutput.innerHTML = loggedUser.user;
emailOutput.innerHTML = loggedUser.email;

function changeView(selected){
    let views = document.getElementsByTagName("section");

    for(i = 0; i < views.length; i++){
        if(views[i].classList.contains(selected) == false){
            views[i].style.display = "none";
        }

        else{
            views[i].style.display = "flex";
        }
    }
}

function editData(){
    alert("No momento, não é possivel alterar as informações")
}

function logoutUser() {    
    sessionStorage.setItem('loggedUser', JSON.stringify({}));
    window.location.href = 'index.html';
}