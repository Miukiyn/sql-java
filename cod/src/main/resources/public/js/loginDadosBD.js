var iframe = document.getElementById("myIframe");
var buttonContainer = document.getElementById("buttonContainer");
var buttonLogout = document.getElementById("buttonLogout");

function updateButtonVisibility() {
    var iframeContent = iframe.contentDocument.body.textContent;

    if (iframeContent.includes("ONG")) {
        // Conteúdo do iframe contém "ONG", mostre o botão de logout
        buttonContainer.style.display = "none";
        buttonLogout.style.display = "block";
        buttonAddAnimal.style.display = "block";
    } else if (iframeContent.includes("User")) {
        buttonContainer.style.display = "none";
        buttonLogout.style.display = "block";
        buttonAddAnimal.style.display = "none";
    } else {
        // Conteúdo não contém "ONG" ou "User", exiba os botões padrão
        buttonContainer.style.display = "block";
        buttonLogout.style.display = "none";
        buttonAddAnimal.style.display = "none";
    }
}

iframe.onload = updateButtonVisibility;
updateButtonVisibility();
