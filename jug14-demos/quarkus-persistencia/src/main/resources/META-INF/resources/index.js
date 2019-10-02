function apaga(id) {
  $.ajax({
    url: '/evento/' + id,
    type: 'DELETE',
    success: function(result) {
      carregaEventos();
    }
  });
}

function adiciona() {
  var evento = {
    nome: $("#nome").val()
  };
  $("#nome").val("");
  $.ajax({
    url: '/evento',
    type: 'PUT',
    headers: {
      "Content-type": "application/json"
    },
    data: JSON.stringify(evento),
    success: function(result) {
      carregaEventos();
    }
  });
}

function carregaEventos() {
  $.get("/evento", function(data, status){
    var listaEvt = document.getElementById("eventos");
    listaEvt.innerHTML = "";
      $.each(data, function(i, evento){
        var evtEl = document.createElement("li");
        var evtApagar =document.createElement("button");
        evtApagar.textContent = "X";
        evtApagar.onclick = function() {
          apaga(evento.id);
        }
        evtEl.textContent = evento.id + ": " + evento.nome;
        evtEl.appendChild(evtApagar);
        listaEvt.appendChild(evtEl);
      });
    });
}

$(document).ready(function() {
  carregaEventos();
});