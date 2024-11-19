

// Intercepta o envio do formulário de criação de turma e envia via AJAX
$('#formCriarTurma').on('submit', function (e) {
    e.preventDefault(); // Impede o envio padrão do formulário

    var formData = $(this).serialize(); // Serializa os dados do formulário

    $.ajax({
        url: '/gerirTurmas/criarTurma', // Endpoint do controlador para criação
        type: 'POST',
        data: formData, // Envia os dados do formulário
        success: function (response) {
            if (response === 'sucesso') {
                $('#modalCriarTurma').modal('hide'); // Fecha o modal
                location.reload(); // Atualiza a página para mostrar a nova turma
            } else {
                alert('Erro ao criar a turma: ' + response); // Mostra mensagem de erro
            }
        },
        error: function (xhr, status, error) {
            console.error('Erro AJAX:', status, error); // Log para depuração
            alert('Erro ao se comunicar com o servidor.');
        }
    });
});

// Captura o evento de abertura do modal de edição para preencher os campos
// Definir o ano atual no campo de "Ano" quando o modal for aberto
$('#modalCriarTurma').on('show.bs.modal', function () {
    var anoAtual = new Date().getFullYear();
    $('#anoTurma').val(anoAtual); // Preenche o campo "Ano" com o ano atual no modal de criação
});

$('#modalEditarTurma').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget); // Botão que acionou o modal
    var turmaId = button.data('id'); // ID da turma
    var serie = button.data('serie'); // Série da turma
    var tipo = button.data('tipo'); // Tipo da turma
    var ano = button.data('ano'); // Ano da turma

    // Preenche os campos do formulário de edição
    $('#idTurmaEditar').val(turmaId);
    $('#serieTurmaEditar').val(serie);
    $('#tipoTurmaEditar').val(tipo);

    // Define o ano no campo, ou usa o ano atual se não houver valor
    var anoAtual = new Date().getFullYear();
    $('#anoTurmaEditar').val(ano || anoAtual);  // Se o ano não for definido, usa o ano atual
});

// Intercepta o envio do formulário de edição de turma via AJAX
$('#formEditarTurma').on('submit', function (e) {
    e.preventDefault(); // Impede o envio padrão do formulário

    var formData = $(this).serialize(); // Serializa os dados do formulário

    $.ajax({
        url: '/gerirTurmas/atualizarTurma', // Endpoint do controlador para edição
        type: 'POST',
        data: formData, // Envia os dados do formulário
        success: function (response) {
            if (response === 'sucesso') {
                $('#modalEditarTurma').modal('hide'); // Fecha o modal
                location.reload(); // Atualiza a página para mostrar a turma editada
            } else {
                alert('Erro ao editar a turma: ' + response); // Mensagem de erro
            }
        },
        error: function (xhr, status, error) {
            console.error('Erro AJAX:', status, error); // Log para depuração
            alert('Erro ao se comunicar com o servidor.');
        }
    });
});

// Captura o evento de abertura do modal de exclusão para configurar o ID da turma
$('#modalExcluirTurma').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget);
    var turmaId = button.data('id');
    $('#idTurmaExcluir').val(turmaId);
});

// Intercepta o envio do formulário de exclusão de turma via AJAX
$('#formExcluirTurma').on('submit', function (e) {
    e.preventDefault(); // Impede o envio padrão do formulário

    var formData = $(this).serialize(); // Serializa os dados do formulário

    $.ajax({
        url: '/gerirTurmas/excluirTurma', // Endpoint do controlador para exclusão
        type: 'POST',
        data: formData, // Envia os dados do formulário
        success: function (response) {
            if (response === 'sucesso') {
                $('#modalExcluirTurma').modal('hide'); // Fecha o modal
                location.reload(); // Atualiza a página para remover a turma
            } else {
                alert('Erro ao excluir a turma: ' + response); // Mensagem de erro
            }
        },
        error: function (xhr, status, error) {
            console.error('Erro AJAX:', status, error); // Log para depuração
            alert('Erro ao se comunicar com o servidor.');
        }
    });
});

// Converter automaticamente o campo de "Turma" para maiúsculas
$('#tipoTurma').on('input', function() {
    $(this).val($(this).val().toUpperCase());
});

$('#tipoTurmaEditar').on('input', function() {
    $(this).val($(this).val().toUpperCase());
});
