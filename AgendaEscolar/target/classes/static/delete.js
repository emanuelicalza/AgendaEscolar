function confirmDeletion(type, itemId, relatedItems, deleteCallback) {
    // Atualize o título do modal com base no tipo
    const title = document.getElementById('confirmDeleteLabel');
    title.innerText = `Confirmação de Exclusão de ${type.charAt(0).toUpperCase() + type.slice(1)}`;

    // Atualize a lista de dependências
    const list = document.getElementById('relatedItemsList');
    list.innerHTML = relatedItems.map(item => `<li>${item}</li>`).join('');

    // Configure o botão de confirmação
    const confirmButton = document.getElementById('confirmDeleteButton');
    confirmButton.onclick = () => {
        deleteCallback(itemId); // Execute a exclusão específica
        const modal = bootstrap.Modal.getInstance(document.getElementById('confirmDeleteModal'));
        modal.hide(); // Feche o modal
    };

    // Exiba o modal
    const modal = new bootstrap.Modal(document.getElementById('confirmDeleteModal'));
    modal.show();
}

function deleteProfessor(professorId) {
    // Exemplo de dependências relacionadas ao professor
    const relatedItems = ['Matéria A', 'Matéria B']; // Substitua por dados dinâmicos
    confirmDeletion('professor', professorId, relatedItems, id => {
        console.log(`Excluindo professor ${id} e dependências...`);
        // Lógica de exclusão real
    });
}

function deleteMateria(materiaId) {
    // Exemplo de dependências relacionadas à matéria
    const relatedItems = ['Turma X', 'Professor Y']; // Substitua por dados dinâmicos
    confirmDeletion('matéria', materiaId, relatedItems, id => {
        console.log(`Excluindo matéria ${id} e dependências...`);
        // Lógica de exclusão real
    });
}

function deleteTurma(turmaId) {
    // Exemplo de dependências relacionadas à turma
    const relatedItems = ['Matéria A', 'Matéria B']; // Substitua por dados dinâmicos
    confirmDeletion('turma', turmaId, relatedItems, id => {
        console.log(`Excluindo turma ${id} e dependências...`);
        // Lógica de exclusão real
    });
}

function openDeleteModal(type, itemId) {
    // Define o texto e configura o botão de exclusão
    const deleteModalText = document.getElementById('deleteModalText');
    const confirmDeleteBtn = document.getElementById('confirmDeleteBtn');

    deleteModalText.innerText = `Tem certeza de que deseja excluir este ${type}?`;

    // Passa o ID para o botão de confirmação
    confirmDeleteBtn.onclick = function() {
        deleteItem(type, itemId); // Chama a função deleteItem para o tipo e id fornecidos
    };

    // Exibe o modal
    const deleteModal = new bootstrap.Modal(document.getElementById('deleteModal'));
    deleteModal.show();
}

function deleteItem(type, id) {
    // Lógica de exclusão, por exemplo:
    fetch(`/delete/${type.toLowerCase()}/${id}`, { method: 'DELETE' })
        .then(response => {
            if (response.ok) {
                alert(`${type} excluído com sucesso!`);
                window.location.reload(); // Atualiza a página após a exclusão
            } else {
                alert(`Erro ao excluir ${type}.`);
            }
        })
        .catch(error => console.error('Erro:', error));
}
