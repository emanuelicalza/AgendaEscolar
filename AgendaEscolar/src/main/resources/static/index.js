// Aguarda o DOM ser completamente carregado
document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');

    // Inicializa o calendário FullCalendar com opções específicas
    var calendar = new FullCalendar.Calendar(calendarEl, {
        themeSystem: 'bootstrap5',
        initialView: 'dayGridMonth',
        locale: 'pt-br', // Configura a linguagem do calendário

        // Evento disparado ao clicar em uma data
        dateClick: function(info) {
            var modal = new bootstrap.Modal(document.getElementById('modal')); // Abre o modal
            modal.show();

            // Limpa o formulário e habilita o botão de salvar
            $('#event-form')[0].reset();
            $('#saveBtn').prop('disabled', false);

            // Evento de envio do formulário para salvar nova prova
            $('#event-form').off('submit').on('submit', function(e) {
                e.preventDefault();

                // Coleta os dados do formulário
                var eventData = {
                    titulo: $('#title').val(),
                    descricao: $('#description').val(),
                    type: $('#type').val(),
                    data: info.dateStr
                };

                // Desabilita o botão de salvar para evitar múltiplos envios
                $('#saveBtn').prop('disabled', true);

                // Chama a função para salvar a prova via AJAX
                salvarProva(eventData, modal, calendar);
            });
        },

        // Carrega eventos de provas existentes via AJAX
        events: function(fetchInfo, successCallback, failureCallback) {
            $.ajax({
                url: '/listarprovas',
                method: 'GET',
                success: function(data) {
                    const events = data.map(function(event) {
                        return {
                            id: event.id,
                            title: event.titulo + ' (' + event.type + ')',
                            start: event.data,
                            description: event.descricao,
                            type: event.tipo
                        };
                    });
                    successCallback(events); // Retorna os eventos carregados
                    carregarProvas();
                },
                error: function(xhr, status, error) {
                    console.error('Erro ao carregar eventos:', error);
                    failureCallback(error);
                }
            });
        },

        // Executa quando um evento é montado no calendário
        eventDidMount: function(info) {
            var eventElement = $(info.el);
            eventElement.attr('id', 'atividade-' + info.event.id); // Atribui um ID ao elemento do evento
        },

        // Evento disparado ao clicar em um evento existente
        eventClick: function(info) {
            // Preenche os campos da modal com os detalhes do evento
            $('#modalTitleDetails').text(info.event.title);
            $('#eventDescriptionDetails').text(info.event.extendedProps.description);
            $('#eventTypeDetails').text(info.event.extendedProps.type);
            $('#eventDateDetails').text(info.event.start.toLocaleDateString('pt-BR'));
            $('#modalTitleDetails').attr('data-event-id', info.event.id);
            $('#eventIdDetails').text(info.event.id);

            // Abre a modal de detalhes do evento
            var modalDetails = new bootstrap.Modal(document.getElementById('detailsModalNew'));
            modalDetails.show();
        }
    });

    // Manipula a abertura do modal de detalhes
    $(document).ready(function() {
        $('#detailsModalNew').on('show.bs.modal', function(event) {
            var button = $(event.relatedTarget);
            var eventId = button.data('event-id');
            $(this).find('#modalTitleDetails').attr('data-event-id', eventId);
            $(this).find('#eventIdDetails').text(eventId);
        });

        // Evento de clique para deletar evento
        $('#deleteEventButton').click(function() {
            var eventId = $('#eventIdDetails').text(); // Obtém o ID do evento
            deleteEvent(eventId); // Chama a função de deleção
        });

$('#editEventButton').click(function() {
    var modal = new bootstrap.Modal(document.getElementById('editModal')); // Abre o modal
    modal.show();
        var eventId = $('#modalTitleDetails').attr('data-event-id');
        var eventTitle = $('#modalTitleDetails').text();
        var eventDescription = $('#eventDescriptionDetails').text();
        var eventType = $('#eventTypeDetails').text();
        var eventDate = $('#eventDateDetails').text();

    // Preenche os campos do modal de edição
    $('#editEventId').val(eventId);
    $('#editTitle').val(eventTitle);
    $('#editDescription').val(eventDescription);
    $('#editType').val(eventType);
    $('#editDate').val(new Date(eventDate).toISOString().split('T')[0]);

});
$('#salvaredit').click(function() {
        // Obtém os valores dos campos de edição

        alert('bah');
        var updatedEventId = $('#editEventId').val();
        var updatedTitle = $('#editTitle').val();
        var updatedDescription = $('#editDescription').val();
        var updatedType = $('#editType').val();
        var updatedDate = $('#editDate').val();

        updateAtividade(updatedEventId, updatedTitle, updatedDescription, updatedType, updatedDate);
    });




    });

    // Renderiza o calendário na tela
    calendar.render();
});

// Função AJAX para salvar uma prova
function salvarProva(eventData, modal, calendar) {
    $.ajax({
        url: '/salvarprova',
        method: 'POST',
        data: eventData,
        success: function(data) {
            // Adiciona o novo evento ao calendário
            calendar.addEvent({
                id: data.id,
                title: data.titulo + ' (' + data.type + ')',
                start: data.data,
                description: data.descricao,
                type: data.tipo
            });
            exibirAviso(data); // Exibe uma mensagem de aviso/sucesso
            modal.hide(); // Fecha o modal
            $('#event-form')[0].reset(); // Limpa o formulário
        },
        error: function(xhr, status, error) {
            console.error('Erro ao salvar a prova:', error);
            $('#saveBtn').prop('disabled', false); // Habilita o botão de salvar novamente em caso de erro
        }
    });
}

// Função AJAX para deletar um evento
function deleteEvent(eventId) {
    console.log('ID do Evento:', eventId);

    $.ajax({
        url: '/evento/deletar/' + eventId,
        method: 'DELETE',
        success: function() {
            console.log('Evento deletado com sucesso');
            $('#detailsModalNew').modal('hide');
            $("#atividade-" + eventId).remove(); // Remove o evento da tela
            carregarProvas();
        },
        error: function() {
            console.log('Erro ao deletar o evento');
        }
    });
}

function updateAtividade(eventId, title, description, type, date) {
    alert('ID: ' + eventId + '\nTítulo: ' + title + '\nDescrição: ' + description + '\nTipo: ' + type + '\nData: ' + date);

    $.ajax({
        url: '/atualizarProva',
        type: 'POST',
        data: {
            id: eventId,
            titulo: title,
            descricao: description,
            tipo: type,
            data: date
        },
        success: function(response) {
            if (response) {
                alert('Evento atualizado com sucesso!');

            } else {
                alert('Erro ao atualizar o evento!');
            }
        },
        error: function(xhr, status, error) {
            alert('Houve um erro ao editar o evento. Tente novamente.');
        }
    });
}



