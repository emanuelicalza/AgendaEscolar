
function salvarCadastrar(element){
    let nome = $("#nome").val();
    let email = $("#emailCadastro").val();
    let senha = $("#senha").val();
    let confirmarSenha = $("#confirmarSenha").val();
    let dataNascimento = $("#dataNascimento").val();

    $.ajax({
            url: "/cadastro",
            method: "post",
            data: {
                nome: nome,
                email: email,
                senha: senha,
                confirmarSenha: confirmarSenha,
                dataNascimento: dataNascimento
            },
            success: function(data) {
                $("#activity-container").append(data);
                $("#nome").val(''); // Limpa o campo de atividade
                $("#email").val(''); // Limpa o campo de data
                $("#senha").val('');
                $("#confirmarSenha").val('');
                $("#dataNascimento").val('');
            },
            error: function() {
                alert("deu ruim");
            }
        });
}

$('#editarProfessorModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget); // Botão que abriu o modal
        var id = button.data('id'); // Extraindo dados do atributo data-* do botão
        var nome = button.data('nome');
        var email = button.data('email');
        var dataNascimento = button.data('nascimento');

        // Preenchendo os campos do modal
        var modal = $(this);
        modal.find('#professorId').val(id);
        modal.find('#nomeEditar').val(nome);
        modal.find('#emailEditar').val(email);
        modal.find('#dataNascimentoEditar').val(dataNascimento);
    });