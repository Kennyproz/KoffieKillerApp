
function getCoffee() {
    $.get('/coffees', function (data) {
        console.log(data);
        setCoffeeContent(data);
    });
}
function setCoffeeContent(data) {
    const container = $('#coffeeContainer');
    container.empty();

    $.each(data, function(index, element) {
        var li = $('<li>').text(element.name).appendTo(container);
        var deleteButton = $('<button>').text(' delete').appendTo(li);
        deleteButton.bind('click', function() {
            deleteCoffee(element.id);
        })

        var editButton = $('<button>').text(' Edit').appendTo(li);
        editButton.bind('click', function() {
            var newName = $("#inputname")+element.id.val();
            editCoffee(element.id,newName);
        })

    });

    if(data.length === 0) {
        $('<p>').text("No cofeee").appendTo(container);
    }
}


function editCoffee(id, newName) {
    $.get('/editCoffee/' + id + '/' + newName, function (data) {
        getCoffee();
    });
}

function deleteCoffee(id) {
    $.get('/deleteCoffee/' + id, function (data) {
        getCoffee();
    });
}