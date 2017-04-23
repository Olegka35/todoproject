/**
 * Created by Олег on 09.03.2017.
 */

$(document).ready(function(){
    $("#results").tablesorter();
    $('tbody tr').addClass('visible');

});

function showEdit(id, name, descr, priority, status) {
        $('input#ID').prop('value', id);
        $('input#name').prop('value', name);
        $('input#descr').prop('value', descr);
        $('select#priority').val(priority);
        $('select#status').val(status);
        $('div.edit').fadeIn(500);
        $("body").append("<div id='overlay'></div>");
        $('#overlay').show().css({'filter' : 'alpha(opacity=80)'});
        return false;
}
function showInsert() {
    $('div.add').fadeIn(500);
    $("body").append("<div id='overlay'></div>");
    $('#overlay').show().css({'filter' : 'alpha(opacity=80)'});
    return false;
}
$(function () {
    $('a.close').click(function () {
        $(this).parent().fadeOut(100);
        $('#overlay').remove('#overlay');
        return false;
    });
});
function getPriority(prior) {
    if(prior == 1)
        return "1. Низкий";
    else if(prior == 2)
        return "2. Средний";
    else if(prior == 3)
        return "3. Высокий";
    else if(prior == 4)
        return "4. Срочный";
}
function getStatus(status) {
    if(status == 1)
        return "1. В ожидании";
    else if(status == 2)
        return "2. В работе";
    else if(status == 3)
        return "3. Завершено";
}

function filter(selector, query) {
    $(selector).each(function() {
        ($(this).text().search(new RegExp(query, "i")) < 0) ? $(this).hide().removeClass('visible') : $(this).show().addClass('visible');
    });
}

function filterTable(type) {
    if(type == 1) {
        $('tbody tr').removeClass('visible').show().addClass('visible');
    }
    else {
        filter('#results tbody tr', getStatus(type-1));
    }
}
