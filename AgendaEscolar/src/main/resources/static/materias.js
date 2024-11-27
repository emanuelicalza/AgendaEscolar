// Intercepta o envio do formulário de criação de matéria e envia via AJAX
$('#formCriarMateria').on('submit', function (e) {
    e.preventDefault(); // Impede o envio padrão do formulário

    var formData = $(this).serialize(); // Serializa os dados do formulário

    $.ajax({
        url: '/gerirMaterias/criarMateria', // Endpoint do controlador para criação
        type: 'POST',
        data: formData, // Envia os dados do formulário
        success: function (response) {
            if (response === 'sucesso') {
                $('#modalCriarMateria').modal('hide'); // Fecha o modal
                location.reload(); // Atualiza a página para mostrar a nova matéria
            } else {
                alert('Erro ao criar a matéria: ' + response); // Mostra mensagem de erro
            }
        },
        error: function (xhr, status, error) {
            console.error('Erro AJAX:', status, error); // Log para depuração
            alert('Erro ao se comunicar com o servidor.');
        }
    });
});

// Captura o evento de abertura do modal de edição para preencher os campos
$('#modalEditarMateria').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget); // O botão que ativou o modal
    var id = button.data('id');
    var nome = button.data('nome'); // Aqui estamos capturando o nome
    var turma = button.data('turma');
    var professorId = button.data('professor-id');

    var modal = $(this);

    // Verifique se o nome da matéria está sendo capturado corretamente
    console.log("Nome da Matéria: ", nome); // Verifique se o nome está sendo capturado

    modal.find('#idMateriaEditar').val(id);
    modal.find('#nomeMateriaEditar').val(nome); // Preenchendo o campo com o nome

    if (turma) {
        modal.find('#turmaMateriaEditar').val(turma); // Preenchendo a turma
    }

    modal.find('#professorMateriaEditar').val(professorId);
});



// Intercepta o envio do formulário de edição e envia via AJAX
$('#formEditarMateria').on('submit', function(e) {
    e.preventDefault();  // Impede o envio padrão do formulário

    var formData = $(this).serialize();  // Coleta os dados do formulário

    $.ajax({
        url: '/gerirMaterias/atualizarMateria',  // Endpoint do controlador
        type: 'POST',  // Usando POST, mas vamos simular PUT via _method
        data: formData,  // Envia os dados do formulário
        success: function(response) {
            $('#modalEditarMateria').modal('hide');  // Fecha o modal
            window.location.href = '/gerirMaterias';  // Redireciona para a lista de matérias
        },
        error: function(xhr, status, error) {
            alert('Erro ao editar a matéria!');
        }
    });
});

// Passa o ID da matéria para o modal de exclusão
$('#modalExcluirMateria').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget);
    var id = button.data('id');
    var modal = $(this);
    modal.find('#idMateriaExcluir').val(id);
});

// Intercepta o envio do formulário de exclusão e envia via AJAX
$('#formExcluirMateria').on('submit', function(e) {
    e.preventDefault();  // Impede o envio padrão do formulário

    var idMateria = $('#idMateriaExcluir').val();  // Coleta o ID da matéria

    $.ajax({
        url: '/gerirMaterias/excluirMateria',  // Endpoint do controlador para exclusão
        type: 'POST',  // Usando POST para enviar a requisição
        data: { id: idMateria },  // Envia o ID da matéria
        success: function(response) {
            if (response === 'sucesso') {
                $('#modalExcluirMateria').modal('hide');  // Fecha o modal
                location.reload();  // Recarrega a página para refletir a exclusão
            } else {
                alert('Erro ao excluir a matéria!');
            }
        },
        error: function(xhr, status, error) {
            alert('Erro ao excluir a matéria!');
        }
    });
});


$(document).ready(function () {
    $.ajax({
        url: '/obterMateriasUsuario', // Endpoint para obter as matérias
        method: 'GET',
        success: function (materias) {
            let select = $('#materiasSelect');
            materias.forEach(materia => {
                // Adiciona as opções ao select
                select.append(new Option(materia.descricaoComTurma, materia.id)); // Exibe a matéria com a turma
            });
        },
        error: function () {
            alert('Erro ao carregar as matérias.');
        }
    });
});
});
