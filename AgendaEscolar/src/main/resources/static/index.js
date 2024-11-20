document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {
        themeSystem: 'bootstrap5',
        initialView: 'dayGridMonth',
        locale: 'pt-br',

        dateClick: function(info) {
            var modal = new bootstrap.Modal(document.getElementById('modal')); // Abre a modal
            modal.show();

            // Limpar o formulário e habilitar novamente os elementos quando o modal for aberto
            $('#event-form')[0].reset(); // Limpa o formulário
            $('#saveBtn').prop('disabled', false); // Habilita o botão de salvar
            $('#event-form').off('submit').on('submit', function(e) { // Usando .off() para garantir que a função de submit não seja anexada múltiplas vezes
                e.preventDefault();

                var eventData = {
                    titulo: $('#title').val(),
                    descricao: $('#description').val(),
                    type: $('#type').val(),
                    data: info.dateStr
                };

                // Desabilita o botão de salvar para evitar múltiplos envios
                $('#saveBtn').prop('disabled', true);

                // Envio de dados via AJAX
                $.ajax({
                    url: '/salvarprova',
                    method: 'POST',
                    data: eventData,
                    success: function(data) {
                        calendar.addEvent({
                            id: data.id,
                            title: data.titulo + ' (' + data.type + ')',
                            start: data.data,  // Data de início
                            description: data.descricao,
                            type: data.tipo
                        });
                        exibirAviso(data);
                        modal.hide(); // Fechar o modal corretamente

                        $('#event-form')[0].reset(); // Limpar o formulário
                    },
                    error: function(xhr, status, error) {
                        console.error('Erro ao salvar a prova:', error);
                        $('#saveBtn').prop('disabled', false); // Reabilitar o botão em caso de erro
                    }
                });
            });
        },

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
                    successCallback(events);
                    carregarProvas();
                },
                error: function(xhr, status, error) {
                    console.error('Erro ao carregar eventos:', error);
                    failureCallback(error);
                }
            });
        },

        eventDidMount: function(info) {
            var eventElement = $(info.el);
            eventElement.attr('id', 'atividade-' + info.event.id);
        },

        eventClick: function(info) {
            $('#modalTitleDetails').text(info.event.title);
            $('#eventDescriptionDetails').text(info.event.extendedProps.description);
            $('#eventTypeDetails').text(info.event.extendedProps.type);
            $('#eventDateDetails').text(info.event.start.toLocaleDateString('pt-BR'));
            $('#modalTitleDetails').attr('data-event-id', info.event.id);
            $('#eventIdDetails').text(info.event.id);

            var modalDetails = new bootstrap.Modal(document.getElementById('detailsModalNew'));
            modalDetails.show();
        }
    });

    $(document).ready(function () {
        // Captura o evento de abertura do modal
        $('#detailsModalNew').on('show.bs.modal', function (event) {
            var button = $(event.relatedTarget); // Botão que disparou o modal
            var eventId = button.data('event-id'); // Extrai o ID da atividade do botão

            // Atribui o ID da atividade ao título do modal
            $(this).find('#modalTitleDetails').attr('data-event-id', eventId);
            $(this).find('#eventIdDetails').text(eventId);  // Exibe o ID no <span> dentro do modal
        });

        // Função para deletar um evento
        function deleteEvent(eventId) {
            console.log('ID do Evento:', eventId); // Verifique se o ID está sendo extraído corretamente

            // Requisição AJAX para deletar o evento
            $.ajax({
                url: '/evento/deletar/' + eventId,
                method: 'DELETE',
                success: function () {
                    console.log('Evento deletado com sucesso');
                    $('#detailsModalNew').modal('hide');
                    $("#atividade-" + eventId).remove();
                    carregarProvas();
                },
                error: function () {
                    console.log('Erro ao deletar o evento');
                }
            });
        }

        // Manipulador de evento para o botão de deletar
        $('#deleteEventButton').click(function () {
            var eventId = $('#eventIdDetails').text(); // Pega o ID do evento diretamente do <span>
            deleteEvent(eventId); // Chama a função deleteEvent
        });

        $('#editEventButton').click(function () {
            var eventId = $('#modalTitleDetails').attr('data-event-id'); // Pega o ID do evento
            var eventTitle = $('#modalTitleDetails').text(); // Título do evento
            var eventDescription = $('#eventDescriptionDetails').text(); // Descrição do evento
            var eventType = $('#eventTypeDetails').text(); // Tipo do evento
            var eventDate = $('#eventDateDetails').text(); // Data do evento

            // Preenche os campos da modal de edição
            $('#editEventId').val(eventId);
            $('#editTitle').val(eventTitle);
            $('#editDescription').val(eventDescription);
            $('#editType').val(eventType);
            $('#editDate').val(new Date(eventDate).toISOString().split('T')[0]); // Converte a data para o formato YYYY-MM-DD

            // Abre a modal de edição
            var editModal = new bootstrap.Modal(document.getElementById('editModal'));
            editModal.show();

               $('#edita').click(function () {
                    deleteEvent(eventId);
                    editModal.hide();
                    $.ajax({
                        url: '/salvarprova',
                        method: 'POST',
                        data: eventData,
                        success: function(data) {
                            calendar.addEvent({
                                id: data.id,
                                title: data.titulo + ' (' + data.type + ')',
                                start: data.data,  // Data de início
                                description: data.descricao,
                                type: data.tipo
                            });
                            exibirAviso(data);
                            modal.hide(); // Fechar o modal corretamente

                            $('#event-form')[0].reset(); // Limpar o formulário
                        },
                        error: function(xhr, status, error) {
                            console.error('Erro ao salvar a prova:', error);
                            $('#saveBtn').prop('disabled', false); // Reabilitar o botão em caso de erro
                        }
                    });
                });
        });
    });
    calendar.render();
});
