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

                // Adicionar o evento no calendário
                calendar.addEvent({
                    title: title + ' (' + type + ')',
                    start: info.dateStr,
                    description: description
                });

                modal.hide(); // Fechar modal
            };
        }
    });

    calendar.render();
});
