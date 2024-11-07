// Função para salvar ou atualizar professor
function salvarProfessor() {
    let id = $("#professorId").val(); // Obtém o ID do campo oculto
    let nome = $("#nome").val();
    let email = $("#email").val();
    let dataNascimento = $("#dataNascimento").val();

    $.ajax({
        url: "/professores/salvar",
        method: "POST",
        data: {
            id: id,
            nome: nome,
            email: email,
            dataNascimento: dataNascimento
        },
        success: function(data) {
            // Fecha o modal
            $("#professorModal").modal('hide');

            // Se for uma atualização
            if (id) {
                // Localiza a linha correspondente e atualiza os dados
                let row = $(`#professor-${id}`);
                row.find(".nome").text(nome);
                row.find(".email").text(email);
                row.find(".dataNascimento").text(dataNascimento);
            } else {
                // Se for uma criação, adiciona a nova linha na tabela
                $("#professoresTabela tbody").append(
                    `<tr id="professor-${data.id}">
                        <td class="nome">${nome}</td>
                        <td class="email">${email}</td>
                        <td class="dataNascimento">${dataNascimento}</td>
                        <td>
                            <button class="btn btn-warning btn-sm" onclick="openProfessorModal(${data.id})">Editar</button>
                            <button class="btn btn-danger btn-sm" onclick="openDeleteModal(${data.id})">Deletar</button>
                        </td>
                    </tr>`
                );
            }


            // Use `atualizarTabelaProfessores` para garantir a atualização completa da tabela
            atualizarTabelaProfessores();

            // Limpa os campos do formulário para o próximo uso
            limparCampos();

        },
        error: function(xhr) {
            alert("Erro ao salvar o professor: " + xhr.responseText);
        }
    });
}


// Função para abrir o modal de edição com os dados do professor
function openProfessorModal(professorId = null) {
    if (professorId) {
        professorEditandoId = professorId; // Armazena o ID do professor para edição
        $("#professorId").val(professorId); // Define o ID no campo oculto

        $.ajax({
            url: `/professores/${professorId}`,
            method: "GET",
            success: function(data) {
                // Preenche o modal com os dados do professor
                $("#nome").val(data.nome);
                $("#email").val(data.email);
                $("#dataNascimento").val(data.dataNascimento);
                $("#modalLabel").text("Editar Professor");
                $("#professorModal").modal('show');

            },
            error: function(xhr) {
                alert("Erro ao carregar os dados do professor: " + xhr.responseText);
            }
        });
    } else {
        professorEditandoId = null; // Reseta o ID para criação de um novo professor
        limparCampos();
        $("#professorId").val(''); // Limpa o ID no campo oculto
        $("#modalLabel").text("Criar Professor");
        $("#professorModal").modal('show');
    }
}

// Função para limpar os campos do formulário
function limparCampos() {
    $("#nome").val('');
    $("#email").val('');
    $("#dataNascimento").val('');
}

// Função para abrir o modal de exclusão de professor
function openDeleteModal(id) {
    // Configura o botão de confirmação para excluir o professor com o ID passado
    $("#confirmDeleteBtn").off('click').on('click', function() {
        excluirProfessor(id); // Passa o ID do professor para a função de exclusão
    });
    $("#deleteModal").modal('show');
}

// Função para excluir professor
function excluirProfessor(id) {
    $.ajax({
        type: 'DELETE',
        url: `/professores/deletar/${id}`,  // Alterado para 'deletar' ao invés de 'excluir'
        success: function() {
            $('#deleteModal').modal('hide');
            atualizarTabelaProfessores(); // Atualiza a tabela após exclusão
        },
        error: function(xhr, status, error) {
            alert('Erro ao excluir professor.');
            console.error("Erro ao excluir professor:", error);
        }
    });
}

// Função para atualizar a tabela de professores após a exclusão
function atualizarTabelaProfessores() {
    $.ajax({
        url: "/professores/listar", // Rota para listar todos os professores
        method: "GET",
        success: function(data) {
            let tabela = $("#professoresTabela tbody");
            tabela.empty(); // Limpa a tabela antes de repovoar

            data.forEach(professor => {
                tabela.append(
                    `<tr id="professor-${professor.id}">
                        <td class="nome">${professor.nome}</td>
                        <td class="email">${professor.email}</td>
                        <td class="dataNascimento">${professor.dataNascimento}</td>
                        <td>
                            <button class="btn btn-warning btn-sm" onclick="openProfessorModal(${professor.id})">Editar</button>
                            <button class="btn btn-danger btn-sm" onclick="openDeleteModal(${professor.id})">Deletar</button>
                        </td>
                    </tr>`
                );
            });
        },
        error: function(xhr) {
            alert("Erro ao carregar a lista de professores.");
        }
    });
}

$(document).ready(function() {
    // Configuração do botão de salvar
    $("#salvarProfessorBtn").click(salvarProfessor);
});
