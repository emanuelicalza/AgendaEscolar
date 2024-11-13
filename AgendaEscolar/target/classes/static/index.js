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
                        id: event.id,  // Assegure-se de que o ID do evento é incluído
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
            // Testar se o ID está sendo recebido corretamente
            console.log('Event ID:', info.event.id);  // Verifica o ID do evento no console

            // Verifique se o ID realmente está sendo passado corretamente
            if (info.event.id) {
                console.log('ID do evento: ', info.event.id);  // Exibe o ID no console
            } else {
                console.log('ID do evento não encontrado!');
            }

            // Preenche os campos da modal com os dados do evento
            document.getElementById('modalTitleDetails').innerText = info.event.title;
            document.getElementById('eventDescriptionDetails').innerText = info.event.extendedProps.description;
            document.getElementById('eventTypeDetails').innerText = info.event.extendedProps.type;
            document.getElementById('eventDateDetails').innerText = info.event.start.toLocaleDateString('pt-BR');

            // Definir o ID do evento no modal
            document.getElementById('modalTitleDetails').setAttribute('data-event-id', info.event.id);
            document.getElementById('eventIdDetails').innerText = info.event.id;  // Exibe o ID na modal

            // Mostrar a modal
            var modalDetails = new bootstrap.Modal(document.getElementById('detailsModalNew'));
            modalDetails.show();
        }
    });

    calendar.render();
});
