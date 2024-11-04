function salvarProfessor(element) {
    // Verifica se estamos criando ou editando pelo valor do ID
    let id = $("#professorId").val();
    let nome = $("#nome").val();
    let email = $("#email").val();
    let dataNascimento = $("#dataNascimento").val();

    let url = id ? `/editarProfessor/${id}` : "/criarProfessor"; // Define a URL com base no ID

    $.ajax({
        url: url,
        method: "post",
        data: {
            id: id,
            nome: nome,
            email: email,
            dataNascimento: dataNascimento
        },
        success: function(data) {
            // Sucesso na operação: atualizar a lista de professores sem recarregar a página
            $("#activity-container").html(data); // Atualize a tabela de professores
            $("#professorModal").modal('hide'); // Fecha o modal
            // Limpa os campos do formulário para o próximo uso
            $("#professorId").val('');
            $("#nome").val('');
            $("#email").val('');
            $("#dataNascimento").val('');
        },
        error: function() {
            alert("Erro ao salvar o professor. Tente novamente.");
        }
    });
}

// Abrir o modal de edição com os dados do professor selecionado
function openProfessorModal(professorId = null) {
    if (professorId) {
        // Carregar dados do professor para edição
        $.get(`/obterProfessor/${professorId}`, function(professor) {
            $('#professorId').val(professor.id);
            $('#nome').val(professor.nome);
            $('#email').val(professor.email);
            $('#dataNascimento').val(professor.dataNascimento);
            $('#modalLabel').text('Editar Professor');
        });
    } else {
        // Abrir o modal para criação de novo professor
        $('#professorId').val('');
        $('#nome').val('');
        $('#email').val('');
        $('#dataNascimento').val('');
        $('#modalLabel').text('Criar Professor');
    }
    $('#professorModal').modal('show');
}

// Abrir o modal de confirmação de exclusão
function openDeleteModal(professorId) {
    $('#confirmDeleteBtn').off('click').on('click', function() {
        $.ajax({
            url: `/deletar-professor/${professorId}`,
            method: "get",
            success: function() {
                $("#activity-container").load("/areaDiretor #activity-container > *"); // Recarrega a tabela
                $('#deleteModal').modal('hide');
            },
            error: function() {
                alert("Erro ao excluir o professor.");
            }
        });
    });
    $('#deleteModal').modal('show');
}

// Evento de botão de salvamento no modal
$("#salvarProfessorBtn").on('click', function() {
    salvarProfessor();
});
