// Declaração global do calendário
var calendario;

document.addEventListener('DOMContentLoaded', function() {
    var elementoCalendario = document.getElementById('calendar');

    calendario = new FullCalendar.Calendar(elementoCalendario, {
        themeSystem: 'bootstrap5',
        initialView: 'dayGridMonth',
        locale: 'pt-br',

        dateClick: function(infoData) {
            var modal = new bootstrap.Modal(document.getElementById('modal'));
            modal.show();

            $('#event-form')[0].reset();
            $('#saveBtn').prop('disabled', false);

            $('#event-form').off('submit').on('submit', function(evento) {
                evento.preventDefault();

                var dadosEvento = {
                    titulo: $('#title').val(),
                    descricao: $('#description').val(),
                    tipo: $('#type').val(),
                    data: infoData.dateStr
                };

                $('#saveBtn').prop('disabled', true);

                salvarProva(dadosEvento, modal, calendario);
            });
        },

        events: function(infoRequisicao, sucessoCallback, erroCallback) {
            $.ajax({
                url: '/listarprovas',
                method: 'GET',
                success: function(dados) {
                    const eventos = dados.map(function(evento) {
                        return {
                            id: evento.id,
                            title: evento.titulo + ' (' + evento.type + ')',
                            start: evento.data,
                            description: evento.descricao,
                            type: evento.tipo
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

        eventDidMount: function(infoEvento) {
            var elementoEvento = $(infoEvento.el);
            elementoEvento.attr('id', 'atividade-' + infoEvento.event.id);
        },

        eventClick: function(infoEvento) {
            $('#modalTitleDetails').text(infoEvento.event.title);
            $('#eventDescriptionDetails').text(infoEvento.event.extendedProps.description);
            $('#eventTypeDetails').text(infoEvento.event.extendedProps.type);
            $('#eventDateDetails').text(infoEvento.event.start.toLocaleDateString('pt-BR'));
            $('#modalTitleDetails').attr('data-event-id', infoEvento.event.id);
            $('#eventIdDetails').text(infoEvento.event.id);

            var modalDetalhes = new bootstrap.Modal(document.getElementById('detailsModalNew'));
            modalDetalhes.show();
        }
    });

    $(document).ready(function() {
        $('#detailsModalNew').on('show.bs.modal', function(evento) {
            var botao = $(evento.relatedTarget);
            var idEvento = botao.data('event-id');
            $(this).find('#modalTitleDetails').attr('data-event-id', idEvento);
            $(this).find('#eventIdDetails').text(idEvento);
        });

        $('#deleteEventButton').click(function() {
            var idEvento = $('#eventIdDetails').text();
            deletarEvento(idEvento);
        });

        $('#editEventButton').click(function() {
            var modalEdicao = new bootstrap.Modal(document.getElementById('editModal'));
            modalEdicao.show();
            var idEvento = $('#modalTitleDetails').attr('data-event-id');
            var tituloEvento = $('#modalTitleDetails').text();
            var descricaoEvento = $('#eventDescriptionDetails').text();
            var tipoEvento = $('#eventTypeDetails').text();
            var dataEvento = $('#eventDateDetails').text(); // formato dd/mm/aaaa

            // Converter a data do formato brasileiro para o formato ISO
            var partesData = dataEvento.split('/');
            var dataFormatada = `${partesData[2]}-${partesData[1].padStart(2, '0')}-${partesData[0].padStart(2, '0')}`;

            $('#editEventId').val(idEvento);
            $('#editTitle').val(tituloEvento);
            $('#editDescription').val(descricaoEvento);
            $('#editType').val(tipoEvento);
            $('#editDate').val(dataFormatada);
        });

        $('#salvaredit').click(function() {
            var idEventoAtualizado = $('#editEventId').val();
            var tituloAtualizado = $('#editTitle').val();
            var descricaoAtualizada = $('#editDescription').val();
            var tipoAtualizado = $('#editType').val();
            var dataAtualizada = $('#editDate').val();

            atualizarAtividade(idEventoAtualizado, tituloAtualizado, descricaoAtualizada, tipoAtualizado, dataAtualizada);
        });
    });

    calendario.render();
});

function salvarProva(dadosEvento, modal, calendario) {
    $.ajax({
        url: '/salvarprova',
        method: 'POST',
        data: dadosEvento,
        success: function(dados) {
            calendario.refetchEvents();
            exibirAviso(dados);
            modal.hide();
            $('#event-form')[0].reset();

        },
        error: function(xhr, status, erro) {
            console.error('Erro ao salvar a prova:', erro);
            $('#saveBtn').prop('disabled', false);
        }
    });
}

function deletarEvento(idEvento) {
    console.log('ID do Evento:', idEvento);

    $.ajax({
        url: '/evento/deletar/' + idEvento,
        method: 'DELETE',
        success: function() {
            console.log('Evento deletado com sucesso');
            $('#detailsModalNew').modal('hide');
            $("#atividade-" + idEvento).remove();
            carregarProvas();
        },
        error: function() {
            console.log('Erro ao deletar o evento');
        }
    });
}

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
                $('#editModal').modal('hide');

                var evento = calendario.getEventById(idEvento);
                if (evento) {
                    evento.remove();
                }

                calendario.refetchEvents();
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

// Enviar o formulário automaticamente quando o usuário selecionar uma matéria
    document.getElementById('materiasSelect').addEventListener('change', function() {
        this.form.submit();
    });
