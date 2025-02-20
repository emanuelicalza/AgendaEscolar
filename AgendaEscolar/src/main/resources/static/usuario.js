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

// Função para abrir o modal de confirmação de exclusão
function openDeleteModal(id) {
    // Atualiza o texto de confirmação na modal
    const deleteModalText = document.getElementById('deleteModalText');
    deleteModalText.textContent = `Tem certeza de que deseja excluir este professor?`;

    // Configura a ação de exclusão ao clicar no botão "Excluir"
    const confirmDeleteBtn = document.getElementById('confirmDeleteBtn');
    confirmDeleteBtn.onclick = function() {
        excluirProfessor(id); // Chama a função para excluir o professor
    };

    // Exibe a modal
    $('#deleteModal').modal('show');
}


// Função para excluir professor
function excluirProfessor(id) {
    $.ajax({
        type: 'DELETE',
        url: `/professores/professor/${id}`,
        success: function() {
            $('#deleteModal').modal('hide');
            confirmDeleteBtn.onclick = null; // Remove o evento anterior
            atualizarTabelaProfessores();
        },
        error: function(xhr, status, error) {
            console.error("Erro ao excluir professor:", xhr.status, xhr.responseText, error);
            alert(`Erro ao excluir professor: ${xhr.responseText}`);
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
                        <td th:text="${professor.dataNascimentoFormatada}"></td>
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
