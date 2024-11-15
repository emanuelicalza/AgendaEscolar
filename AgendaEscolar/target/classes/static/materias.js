// Passa os dados da matéria para o modal de edição
    $('#modalEditarMateria').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var id = button.data('id');
        var nome = button.data('nome');
        var professorId = button.data('professor-id');

        var modal = $(this);
        modal.find('#idMateriaEditar').val(id);
        modal.find('#nomeMateriaEditar').val(nome);
        modal.find('#professorMateriaEditar').val(professorId);
    });

    // Passa o ID da matéria para o modal de exclusão
    $('#modalExcluirMateria').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var id = button.data('id');
        var modal = $(this);
        modal.find('#idMateriaExcluir').val(id);
    });