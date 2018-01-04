function getTestPage(url) {
    $.get(url, function (data) {
        setPageContent(data);
    });
}

function setPageContent(data) {
    const container = $('#pageContent');
    container.empty();
    const html = $.parseHTML(data);
    container.append(html);
}