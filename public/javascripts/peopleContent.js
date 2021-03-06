
function getPeople() {
    $.get('/persons', function (data) {
        console.log(data);
        setPeopleContent(data);
    });
}
function setPeopleContent(data) {
    const container = $('#peopleContainer');
    container.empty();

    $.each(data, function(index, element) {
        var li = $('<li>').text(element.name).appendTo(container);
        var deleteButton = $('<button>').text(' delete').appendTo(li);
        deleteButton.bind('click', function() {
            deletePerson(element.id);
        })

        var editButton = $('<button>').text(' Edit').appendTo(li);
        editButton.bind('click', function() {
            var newName = $("#inputname")+element.id.val();
            editPerson(element.id,newName);
        })

    });

    if(data.length === 0) {
        $('<p>').text("No people").appendTo(container);
    }
}


function editPerson(id, newName) {
    $.get('/editPerson/' + id + '/' + newName, function (data) {
        getPeople();
    });
}

function deletePerson(id) {
    $.get('/deletePerson/' + id, function (data) {
        getPeople();
    });
}