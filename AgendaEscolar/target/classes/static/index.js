// Declaração global do calendário e tipo de usuário
var calendario;
var usuarioTipo = document.getElementById('info-usuario').getAttribute('data-usuario-tipo');



// Evento disparado quando o DOM é totalmente carregado 
document.addEventListener('DOMContentLoaded', function() {
    // Seleciona o elemento do calendário
    var elementoCalendario = document.getElementById('calendar');

    // Inicialização do FullCalendar
    calendario = new FullCalendar.Calendar(elementoCalendario, {
        // Configurações do sistema de tema e localização
        themeSystem: 'bootstrap5',
        initialView: 'dayGridMonth',
        locale: 'pt-br',
        headerToolbar: {
                left: '',  // "today" será traduzido para "Hoje"
                center: 'title',
                right: 'prev,today,next'
            },
            buttonText: {
                        today: 'Hoje'  // Renomeia manualmente o botão 'today' para 'Hoje'
                    },

        // Evento de clique em uma data do calendário
        dateClick: function(infoData) {
            // Verifica se o usuário tem permissão para adicionar eventos (tipo 2 ou 3)
            if (usuarioTipo == 2 || usuarioTipo==3){
                // Abre o modal para adicionar novo evento
                var modal = new bootstrap.Modal(document.getElementById('modal'));
                modal.show();

                // Limpa o formulário e habilita o botão de salvar
                $('#event-form')[0].reset();
                $('#saveBtn').prop('disabled', false);

                // Configuração do evento de submit do formulário
                $('#event-form').off('submit').on('submit', function(evento) {
                    evento.preventDefault();

                    // Coleta os dados do novo evento
                    var dadosEvento = {
                        titulo: $('#title').val(),
                        descricao: $('#description').val(),
                        tipo: $('#type').val(),
                        data: infoData.dateStr
                    };

                    // Desabilita o botão de salvar para evitar múltiplos envios
                    $('#saveBtn').prop('disabled', true);

                    // Chama função para salvar o evento
                    salvarProva(dadosEvento, modal, calendario);
                });
            }else{
                // Tratamento para usuários sem permissão
            };
        },

        // Função para carregar eventos do servidor
events: function(infoRequisicao, sucessoCallback, erroCallback) {
    // Obtém o ID da matéria da sessão ou de algum outro lugar
    const materiaId = $('#materiasSelect').val() || ''; // Assume que você tem um select com ID materiasSelect

    $.ajax({
        url: '/listarprovas',
        method: 'GET',
        data: {
            materiaId: materiaId
        },
        success: function(dados) {
            const eventos = dados.map(function(evento) {
                let backgroundColor, borderColor;
                switch(evento.tipo.toLowerCase()) {
                    case 'prova':
                        backgroundColor = '#FF5252';  // Vermelho vivo
                        borderColor = '#D32F2F';      // Vermelho escuro
                        break;
                    case 'trabalho':
                        backgroundColor = '#FF9800';  // Laranja vivo
                        borderColor = '#F57C00';      // Laranja escuro
                        break;
                    case 'aviso':
                        backgroundColor = '#FFEB3B';  // Amarelo vivo
                        borderColor = '#FBC02D';      // Amarelo escuro
                        break;
                    default:
                        backgroundColor = '#9C27B0';  // Roxo vivo para outros tipos
                        borderColor = '#6A1B9A';      // Roxo escuro
                }

                return {
                    id: evento.id,
                    title: evento.titulo + ' (' + evento.tipo + ')',
                    start: evento.data,
                    description: evento.descricao,
                    type: evento.tipo,
                    backgroundColor: backgroundColor,
                    borderColor: borderColor,
                    textColor: '#000000' // Agora em preto
                };
            });
            sucessoCallback(eventos);
            carregarProvas();
        },
        error: function(xhr, status, erro) {
            console.error('Erro ao carregar eventos:', erro);
            erroCallback(erro);
        }
    });
},
        // Evento disparado após a montagem de cada evento no calendário
        eventDidMount: function(infoEvento) {
            var elementoEvento = $(infoEvento.el);
            elementoEvento.attr('id', 'atividade-' + infoEvento.event.id);
        },

        // Evento de clique em um evento existente
        eventClick: function(infoEvento) {
            // Preenche o modal de detalhes com informações do evento
            $('#modalTitleDetails').text(infoEvento.event.title);
            $('#eventDescriptionDetails').text(infoEvento.event.extendedProps.description);
            $('#eventTypeDetails').text(infoEvento.event.extendedProps.type);
            $('#eventDateDetails').text(infoEvento.event.start.toLocaleDateString('pt-BR'));
            $('#modalTitleDetails').attr('data-event-id', infoEvento.event.id);
            $('#eventIdDetails').text(infoEvento.event.id);

            // Abre o modal de detalhes
            var modalDetalhes = new bootstrap.Modal(document.getElementById('detailsModalNew'));
            modalDetalhes.show();
        }
    });

    // Eventos jQuery após o carregamento do documento
    $(document).ready(function() {
        // Configuração do modal de detalhes
        $('#detailsModalNew').on('show.bs.modal', function(evento) {
            var botao = $(evento.relatedTarget);
            var idEvento = botao.data('event-id');
            $(this).find('#modalTitleDetails').attr('data-event-id', idEvento);
            $(this).find('#eventIdDetails').text(idEvento);
        });

        // Botão de deletar evento
        $('#deleteEventButton').click(function() {
            var idEvento = $('#eventIdDetails').text();
            deletarEvento(idEvento);
        });

        // Botão de editar evento
        $('#editEventButton').click(function() {
            // Abre o modal de edição e preenche com os dados do evento
            var modalEdicao = new bootstrap.Modal(document.getElementById('editModal'));
            modalEdicao.show();
            var idEvento = $('#modalTitleDetails').attr('data-event-id');
            var tituloEvento = $('#modalTitleDetails').text();
            var descricaoEvento = $('#eventDescriptionDetails').text();
            var tipoEvento = $('#eventTypeDetails').text();
            var dataEvento = $('#eventDateDetails').text(); // formato dd/mm/aaaa

            // Converte a data do formato brasileiro para o formato ISO
            var partesData = dataEvento.split('/');
            var dataFormatada = `${partesData[2]}-${partesData[1].padStart(2, '0')}-${partesData[0].padStart(2, '0')}`;

            // Preenche o formulário de edição
            $('#editEventId').val(idEvento);
            $('#editTitle').val(tituloEvento);
            $('#editDescription').val(descricaoEvento);
            $('#editType').val(tipoEvento);
            $('#editDate').val(dataFormatada);
        });

        // Botão de salvar edição
        $('#salvaredit').click(function() {
            // Coleta os dados atualizados do evento
            var idEventoAtualizado = $('#editEventId').val();
            var tituloAtualizado = $('#editTitle').val();
            var descricaoAtualizada = $('#editDescription').val();
            var tipoAtualizado = $('#editType').val();
            var dataAtualizada = $('#editDate').val();

            // Chama função para atualizar a atividade
            atualizarAtividade(idEventoAtualizado, tituloAtualizado, descricaoAtualizada, tipoAtualizado, dataAtualizada);
        });
    });

    // Renderiza o calendário
    calendario.render();
});

// Função para salvar uma nova prova/evento
function salvarProva(dadosEvento, modal, calendario) {
    $.ajax({
        url: '/salvarprova',
        method: 'POST',
        data: dadosEvento,
        success: function(dados) {
            // Atualiza os eventos do calendário
            calendario.refetchEvents();
            // Exibe aviso de sucesso
            exibirAviso(dados);
            // Fecha o modal e reseta o formulário
            modal.hide();
            $('#event-form')[0].reset();
        },
        error: function(xhr, status, erro) {
            console.error('Erro ao salvar a prova:', erro);
            $('#saveBtn').prop('disabled', false);
        }
    });
}

// Função para deletar um evento
function deletarEvento(idEvento) {
    console.log('ID do Evento:', idEvento);

    $.ajax({
        url: '/evento/deletar/' + idEvento,
        method: 'DELETE',
        success: function() {
            console.log('Evento deletado com sucesso');
            // Fecha o modal de detalhes
            $('#detailsModalNew').modal('hide');
            // Remove o elemento do evento da página
            $("#atividade-" + idEvento).remove();
            // Recarrega as provas
            carregarProvas();
        },
        error: function() {
            console.log('Erro ao deletar o evento');
        }
    });
}

// Função para atualizar uma atividade/evento
function atualizarAtividade(idEvento, titulo, descricao, tipo, data) {
    $.ajax({
        url: '/atualizarProva',
        type: 'POST',
        data: {
            id: idEvento,
            titulo: titulo,
            descricao: descricao,
            tipo: tipo,
            data: data
        },
        success: function(resposta) {
            if (resposta) {
                // Fecha o modal de edição
                $('#editModal').modal('hide');

                // Remove o evento antigo do calendário
                var evento = calendario.getEventById(idEvento);
                if (evento) {
                    evento.remove();
                }

                // Atualiza os eventos do calendário
                calendario.refetchEvents();
                // Fecha o modal de detalhes
                $('#detailsModalNew').modal('hide');

                alert('Evento atualizado com sucesso!');
            } else {
                alert('Erro ao atualizar o evento!');
            }
        },
        error: function(xhr, status, erro) {
            alert('Houve um erro ao editar o evento. Tente novamente.');
        }
    });
}

// Evento para submeter formulário de matérias automaticamente
document.getElementById('materiasSelect').addEventListener('change', function() {
    this.form.submit();
});

// Opcional: Adicione este script se quiser personalizar o comportamento da modal
    document.addEventListener('DOMContentLoaded', function() {
        var logoutModal = document.getElementById('logoutModal');
        logoutModal.addEventListener('show.bs.modal', function (event) {
            // Você pode adicionar lógicas personalizadas antes de mostrar a modal, se necessário
        });
    });
