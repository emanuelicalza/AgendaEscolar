document.addEventListener('DOMContentLoaded', function () {
    // Elementos dos botões
    var btnLogin = document.getElementById('btnLogin');
    var btnCadastro = document.getElementById('btnCadastro');

    // Contêiner para carregar os modais
    var modalContainer = document.getElementById('modal-container');

    // Carregar o modal de Login
    btnLogin.addEventListener('click', function () {
        loadModal('login.html', '#loginModal');
    });

    // Carregar o modal de Cadastro
    btnCadastro.addEventListener('click', function () {
        loadModal('cadastro.html', '#cadastroModal');
    });

    // Função para carregar o modal via AJAX
    function loadModal(url, modalId) {
        var xhr = new XMLHttpRequest();
        xhr.open('GET', url, true);
        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4 && xhr.status == 200) {
                // Inserir o modal no contêiner
                modalContainer.innerHTML = xhr.responseText;

                // Mostrar o modal carregado
                var modal = new bootstrap.Modal(document.querySelector(modalId));
                modal.show();
            }
        };
        xhr.send();
    }
});
