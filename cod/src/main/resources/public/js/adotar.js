let filterString = "";

function changeFilter(str){
    if(filterString.includes(str) == true){
        filterString = filterString.replace(str, "");
    }
    
    else{
        filterString = filterString.concat(str);
    }

    console.log(filterString);
    filter()
}

function filter(){
    postedSliderContainer.innerHTML = "";
    if(filterString != ""){
        for(let i = dbAnimal.animais.length-1; i >= 0; i--){
            if(filterString.includes(dbAnimal.animais[i].tipo.toLowerCase()) || (dbAnimal.animais[i].descricao.toLowerCase()).includes(filterString)){
                postedSliderContainer.innerHTML += `
                    <div class="sliderItem flexColumnCenter scaleHover" onclick="animalDetails(${i})">
                        <img class="photo" src="https://source.unsplash.com/random/300x${200 + i}/?${dbAnimal.animais[i].tipo}" alt="Foto">
                        <span class="local">Nome: ${dbAnimal.animais[i].nome}</span>
                        <span class="local">ONG: ${dbUsuarios[dbAnimal.animais[i].ongID].user}</span>
                        <span class="local">Local: ${dbUsuarios[dbAnimal.animais[i].ongID].city}</span>
                    </div>
                `
            }
        }
    }

    else{
        for(let i = dbAnimal.animais.length-1; i >= 0; i--){
            postedSliderContainer.innerHTML += `
                <div class="sliderItem flexColumnCenter scaleHover" onclick="animalDetails(${i})">
                    <img class="photo" src="https://source.unsplash.com/random/300x${200 + i}/?${dbAnimal.animais[i].tipo}" alt="Foto">
                    <span class="nome">Nome: ${dbAnimal.animais[i].nome}</span>
                    <span class="ong">ONG: ${dbUsuarios[dbAnimal.animais[i].ongID].user}</span>
                    <span class="local">Local: ${dbUsuarios[dbAnimal.animais[i].ongID].city}</span>
                    <span class="id" style="display: none">${i}></span>
                </div>
            `
        }
    }
}