document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {
        themeSystem: 'bootstrap5',
        initialView: 'dayGridMonth',
        locale: 'pt-br', // Definir a localidade para português
        dateClick: function(info) {
            var modal = new bootstrap.Modal(document.getElementById('eventModal'));
            modal.show();

            // Salvar a data do evento
            document.getElementById('event-form').onsubmit = function(e) {
                e.preventDefault();

                var title = document.getElementById('title').value;
                var description = document.getElementById('description').value;
                var type = document.getElementById('type').value;

                // Montar os dados para enviar
                var eventData = {
                    titulo: title,
                    descricao: description,
                    type: type,
                    data: info.dateStr
                };

                fetch('/salvarprova', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded' // Necessário para o Spring interpretar os dados
                    },
                    body: new URLSearchParams(eventData) // Serializando os dados
                })
                .then(response => response.text())
                .then(data => {
                    console.log(data);

                    // Adiciona o evento ao calendário
                    calendar.addEvent({
                        title: title + ' (' + type + ')',
                        start: info.dateStr,
                        description: description // Adicionando a descrição aqui para referência
                    });

                    // Fechar a modal após adicionar o evento
                    modal.hide();

                    // Limpar os campos do formulário
                    document.getElementById('event-form').reset();

                    // Exibir aviso
                    exibirAviso(title, description, type, info.dateStr);
                })
                .catch(error => console.error('Erro ao salvar a prova:', error));
            };
        },
        events: function(fetchInfo, successCallback, failureCallback) {
            fetch('/listarprovas')
                .then(response => response.json())
                .then(data => {
                    const events = data.map(event => ({
                        title: event.titulo + ' (' + event.type + ')',
                        start: event.data,
                        description: event.descricao
                    }));
                    successCallback(events);
                })
                .catch(error => {
                    console.error('Erro ao carregar eventos:', error);
                    failureCallback(error);
                });
        },
        eventClick: function(info) {
            // Abrir modal e preencher com dados do evento
            document.getElementById('modalTitleDetails').innerText = info.event.title;
            document.getElementById('eventDescriptionDetails').innerText = info.event.extendedProps.description;
            document.getElementById('eventTypeDetails').innerText = info.event.extendedProps.type;
            document.getElementById('eventDateDetails').innerText = info.event.start.toLocaleDateString('pt-BR');

            // Mostrar a modal
            var modalDetails = new bootstrap.Modal(document.getElementById('detailsModalNew'));
            modalDetails.show();
        }
    });

    calendar.render();

});
