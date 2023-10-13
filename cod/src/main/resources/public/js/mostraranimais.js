// Use AJAX para buscar animais aleatórios do seu endpoint
fetch("/animais")
.then(response => response.json())
.then(data => {
    // Manipule os dados e exiba os animais na página
    const container = document.querySelector('.sliderItemsContainer');
    data.forEach(animal => {
        const animalContainer = document.createElement('div');
        animalContainer.className = 'animalContainer';

        const animalItem = document.createElement('div');
        animalItem.className = 'animalItem';
        animalItem.innerHTML = `
            <img src="${animal.fotoURL}" alt="${animal.nome}">
            <h3>${animal.nome}</h3>
            <p><strong>Raça:</strong> ${animal.raca}</p>
            <p><strong>Espécie:</strong> ${animal.especie}</p>
            <p><strong>Descrição:</strong> ${animal.descricao}</p>
            <p><strong>Característica:</strong> ${animal.caracteristica}</p>
            <p><strong>Tamanho:</strong> ${animal.tamanho}</p>
            <p><strong>Estado:</strong> ${animal.estado}</p>
            <p><strong>Cidade:</strong> ${animal.cidade}</p>
        `;
        animalContainer.appendChild(animalItem);
        container.appendChild(animalContainer);
    });
})
.catch(error => console.error('Erro ao buscar animais:', error));