document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');

    var calendar = new FullCalendar.Calendar(calendarEl, {
        themeSystem: 'bootstrap5',
        initialView: 'dayGridMonth',
        locale: 'pt-br', // Definir a localidade para português
        dateClick: function(info) {
            // Abrir o modal ao clicar em um dia
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

                // Fazer a requisição POST para o backend (controller)
                fetch('/salvarprova', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded' // Necessário para o Spring interpretar os dados
                    },
                    body: new URLSearchParams(eventData) // Serializando os dados
                })
                .then(response => response.text()) // Converter a resposta para texto
                .then(data => {
                    console.log(data); // Exibe a resposta do servidor (ex: "Prova salva com sucesso!")

                    // Adiciona o evento ao calendário
                    calendar.addEvent({
                        title: title + ' (' + type + ')',
                        start: info.dateStr,
                        description: description
                    });

                    modal.hide(); // Fecha o modal após o envio
                })
                .catch(error => console.error('Erro ao salvar a prova:', error));
            };
        },
        events: function(fetchInfo, successCallback, failureCallback) {
            fetch('/listarprovas') // Alterar para a rota que retorna os eventos do banco
                .then(response => response.json())
                .then(data => {
                    // Formatar os dados para o FullCalendar
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
        }
    });

    calendar.render();
});
