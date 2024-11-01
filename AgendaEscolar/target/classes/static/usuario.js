
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