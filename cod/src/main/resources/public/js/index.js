if(JSON.parse(sessionStorage.getItem('dbAnimal')) == null){

    sessionStorage.setItem('dbAnimal', JSON.stringify({animais: [
        { nome: "Belinha", tipo: "Cachorro", raca: "Poodle", idade: "8", tamanho: "médio", descricao: "Bagunceira", ongID: "1" },
        { nome: "Nina", tipo: "Gato", raca: "Norueguês", idade: "3", tamanho: "pequeno", descricao: "MUITO Bagunceira. Destruidora de brinquedos!", ongID: "1" },
        { nome: "Marchal", tipo: "Gato", raca: "Siamês", idade: "3", tamanho: "pequeno", descricao: "Só aceita rações caras.", ongID: "1" },
        { nome: "lora", tipo: "Passaro", raca: "Tucano", idade: "1", tamanho: "pequeno", descricao: "Bastante peludinha.", ongID: "1" },
        { nome: "lora", tipo: "Cachorro", raca: "Golden retriever", idade: "7", tamanho: "grande", descricao: "Bastante animado e agitado. Adora bolinhas de morder.", ongID: "1" }
    ]}))
}

function animalDetails(id){
    modalAnimalDetails = document.getElementById("modalAnimalDetails");
    modalAnimalDetails.innerHTML = `
        <div class="modalItem flexColumnStartStart" onclick="event.stopPropagation()">
            <img class="logo" src="https://source.unsplash.com/random/300x${200 + id}/?${dbAnimal.animais[id].tipo}" alt="${dbAnimal.animais[id].tipo}">
            <span>Nome: ${dbAnimal.animais[id].nome}</span>
            <span>Raça: ${dbAnimal.animais[id].raca}</span>
            <span>Idade: ${dbAnimal.animais[id].idade}</span>
            <span>Descrição: ${dbAnimal.animais[id].descricao}</span>
            <span>ONG Responsável: ${dbUsuarios[dbAnimal.animais[id].ongID].user}</span>
            <span>Contato: ${dbUsuarios[dbAnimal.animais[id].ongID].email}</span>
        </div>
    `
    LrOpenModal("modalAnimalDetails");
}

function printAnimalSlider(){
    dbUsuarios = JSON.parse(sessionStorage.getItem('dbUsuarios'));
    dbAnimal = JSON.parse(sessionStorage.getItem('dbAnimal'));
    postedSliderContainer = document.querySelector("#postedSlider .sliderWindow .sliderItemsContainer");
    console.log(dbAnimal);

    if(dbAnimal.animais.length < 1){

    }

    else{
        /*
        let index = 0;
        let interval = setInterval(function(){
            if(index == dbAnimal.animais.length){
                clearInterval(interval);
                console.log("XX");
            }
            else{
                postedSliderContainer.innerHTML += `
                    <div class="sliderItem flexColumnCenter scaleHover">
                        <img class="photo" src="https://source.unsplash.com/random/200x${300 + index}/?${dbAnimal.animais[index].tipo}" alt="Foto">
                        <span class="local">Nome: ${dbAnimal.animais[index].nome}</span>
                        <span class="local">ONG: ${dbUsuarios[dbAnimal.animais[index].ongID].user}</span>
                        <span class="local">Local: ${dbUsuarios[dbAnimal.animais[index].ongID].city}</span>
                    </div>
                `
            }            
            
            index++;
        }, 1000)*/
        
        
        for(let i = dbAnimal.animais.length-1; i >= 0; i--){
                console.log(i);
                postedSliderContainer.innerHTML += `
                <div class="sliderItem flexColumnCenter scaleHover" onclick="animalDetails(${i})">
                    <img class="photo" src="https://source.unsplash.com/random/300x${200 + i}/?${dbAnimal.animais[i].tipo}" alt="Foto">
                    <span class="nome">Nome: ${dbAnimal.animais[i].nome}</span>
                    <span class="ong">ONG: ${dbUsuarios[dbAnimal.animais[i].ongID].user}</span>
                    <span class="local">Local: ${dbUsuarios[dbAnimal.animais[i].ongID].city}</span>
                </div>
            `
        }
    }
}

printAnimalSlider();