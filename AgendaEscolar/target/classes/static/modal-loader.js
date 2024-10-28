$(document).ready(function () {
    // Elementos dos botões
    var btnLogin = $('#btnLogin');
    var btnCadastro = $('#btnCadastro');

    // Contêiner para carregar os modais
    var modalContainer = $('#modal-container');

    // Mostrar o modal de Login
    btnLogin.on('click', function () {
        $('#loginModal').modal('show');
    });

    // Mostrar o modal de Cadastro
    btnCadastro.on('click', function () {
        $('#cadastroModal').modal('show');
    });
});
