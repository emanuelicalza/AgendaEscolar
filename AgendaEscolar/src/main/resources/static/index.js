document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {
        themeSystem: 'bootstrap5',
        initialView: 'dayGridMonth',
        locale: 'pt-br',

        dateClick: function(info) {
            var modal = new bootstrap.Modal(document.getElementById('modal')); // Abre a modal
            modal.show();

            // Salvar evento ao submeter o formulário
            $('#event-form').submit(function(e) {
                e.preventDefault();

                var eventData = {
                    titulo: $('#title').val(),
                    descricao: $('#description').val(),
                    type: $('#type').val(),
                    data: info.dateStr
                };

                // Envio de dados via AJAX
                $.ajax({
                    url: '/salvarprova',
                    method: 'POST',
                    data: eventData,
                    success: function(data) {

                    },
                    error: function(xhr, status, error) {
                        console.error('Erro ao salvar a prova:', error);
                    }
                });
            });
            $('#saveBtn').click(function() {
                var modal = bootstrap.Modal.getInstance(document.getElementById('modal'));
                modal.hide();
            });
        },

        events: function (fetchInfo, successCallback, failureCallback) {
            $.ajax({
                url: '/listarprovas',
                method: 'GET',
                success: function(data) {
                    const events = data.map(function(event) {
                        return {
                            id: event.id,
                            title: event.titulo + ' (' + event.type + ')',
                            start: event.data,
                            description: event.descricao
                        };
                    });
                    successCallback(events);

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

            // Manipulador de evento para o botão de deletar
            $('#deleteEventButton').click(function () {
                var eventId = $('#eventIdDetails').text(); // Pega o ID do evento diretamente do <span>

                console.log('ID do Evento:', eventId); // Verifique se o ID está sendo extraído corretamente

                // Requisição AJAX para deletar o evento
                $.ajax({
                    url: '/evento/deletar/' + eventId,
                    method: 'DELETE',
                    success: function () {
                        console.log('Evento deletado com sucesso');
                        $('#detailsModalNew').modal('hide'); // Fecha o modal após a exclusão
                        $("#atividade-" + eventId).remove(); // Remove o evento da interface
                    },
                    error: function () {
                        console.log('Erro ao deletar o evento');
                    }
                });
            });
        });
    calendar.render();
});
