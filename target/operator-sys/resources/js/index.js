/**
 * Created by Олег on 09.03.2017.
 */

$(document).ready(function(){
    $(".article_row").click(function () {
        $.post('/article', {'id': this.id}, function (data) {
            if(data == "") {
                $.msgGrowl({
                    type: 'error',
                    title: 'Article select',
                    text: 'The article cannot be read'
                });
            }
            else
                showArticleInfo(data);
        });
    });

    var page = parseInt($('#page').val());
    $('#PrevPage').click(function() {
        goPage(page-1)
    });
    $('#NextPage').click(function() {
        goPage(page+1)
    });
    $(".delbasket").click(function () {
        deleteFromBasket(this.id.substr(14));
    });
});

function showArticleInfo(article)
{
    $('#description').empty();
    $('#description').append("<b>Model:</b> " + article.type + "</br>");
    $('#description').append("<b>Price: </b>" + article.price + "$</br>");
    $('#description').append("<b>On stock: </b>" + article.num + "</br>");
    $.each(article.details, function(key, value) {
        $('#description').append("<b>" + key + ": </b>" + value + "</br>");
    });
    $('#description').append("<br/><input id='number_basket' type='number' value='1' min='0' max='" + article.num + "'><button id='basket'>ADD TO BASKET</button><br/><br/>");
    $('#description').append("<button id='edit'>EDIT</button><br/><br/>");
    $('#description').append("<button id='delete'>DELETE</button><br/><br/>");
    $('#basket').click(function () {
        addToBasket(article);
    });
    $('#edit').click(function () {
        showEdit(article);
    });
    $('#delete').click(function () {
        $.post('/index/delete', {'id': article.id}, function (data) {
            window.location.reload();
        });
    });
}

function showEdit(article) {
    $('input#edit_ID').prop('value', article.id);
    $('input#edit_Type').prop('value', article.type);
    $('input#edit_Price').prop('value', article.price);
    $('input#edit_Num').prop('value', article.num);
    $.each(article.details, function(key, value) {
        $('input#edit_' + key).prop('value', value);
    });
    $("#popup_edit").modal('show');
    return false;
}

function addToBasket(article) {
    var num = Number.parseInt($('#number_basket').val());
    if(num < 0 || num > article.num) {
        $.msgGrowl({
            type: 'error',
            title: 'Incorrect num',
            text: 'Input number is incorrect or out of stock'
        });
    }
    else {
        $.ajax({
            type: "POST",
            url: "/add_to_basket",
            contentType: "application/json",
            data: JSON.stringify({num: num, article_id: article.id}),
            dataType: "json",
            cache: false,
            success: function(data) {
                if(data.error === true) {
                    $.msgGrowl({
                        type: 'error',
                        title: 'Basket',
                        text: data.text
                    });
                }
                else {
                    $.msgGrowl({
                        type: 'success',
                        title: 'Basket',
                        text: 'Item was added in your basket. Go there to make on order'
                    });
                }
            }
        });
    }
}

function sort(field)
{
    $.ajax({
        type: "POST",
        url: "sort",
        contentType: "application/json",
        data: ({"field": field}),
        dataType: "json",
        success: function(data) {
            updateTable(data);
        },
        error: function (error) {
            alert("ERROR!");
        }
    });
}

function filterTable(field) {
    var val;
    if(field == 'status')
        val = parseInt($('#filterStatus').val());
    else if(field == 'priority')
        val = parseInt($('#filterPriority').val());
    $.ajax({
        type: "POST",
        url: "filter",
        contentType: "application/json",
        data: JSON.stringify({field: field, value: val}),
        dataType: "json",
        success: function(data) {
            updateTable(data);
        },
        error: function (error) {
            alert("ERROR!");
        }
    });
}

function searchArticle() {
    var text = $('#search').val();
    $.ajax({
        type: "POST",
        url: "search",
        contentType: "application/json",
        data: JSON.stringify({text: text}),
        dataType: "json",
        success: function(data) {
            updateTable(data);
        },
        error: function (error) {
            alert("ERROR!");
        }
    });
}

function addRow(article)
{
    $('#table_body').append('<tr id="' + article.id + '" class="article_row"></tr>');
    $('#' + article.id).append('<td>' + article.id + '</td>');
    $('#' + article.id).append('<td class="wordwrap">' + article.type + '</td>');
    $('#' + article.id).append('<td class="wordwrap">' + article.price + '$</td>');
    $('#' + article.id).append('<td class="wordwrap">' + article.num + '</td>');
    $('#' + article.id).click(function () {
        $.post('/article', {'id': this.id}, function (data) {
            showArticleInfo(data);
        });
    });
}

function goPage(page)
{
    var pageNum = $('#pageNum').val();
    if(!(page < 1 || page > pageNum)) {
        $.ajax({
            type: "POST",
            url: "page",
            contentType: "application/json",
            data: ({"page": page}),
            dataType: "json",
            success: function(data) {
                updateTable(data);
            },
            error: function (error) {
                alert("ERROR!");
            }
        });
    }
}

function updateTable(data) {
    $('.article_row').remove();
    var list = data.articleList;
    list.forEach(function (task) {
        addRow(task);
    });
    $('#pageNum').val(data.pageNum);
    $('#PrevPage').unbind('click').click(function() {
        goPage(data.page-1)
    });
    $('#NextPage').unbind('click').click(function() {
        goPage(data.page+1)
    });
    $('#PageCounter').html("PAGE " + data.page + " OF " + data.pageNum);
}

function formatDate(datestr) {
    return datestr.split(' ')[2] + ' ' + datestr.split(' ')[1] + ' ' + datestr.split(' ')[5];
}

function formatDateUNIX(date) {
    var newDate = new Date(date).toString();
    return newDate.split(' ')[2] + ' ' + newDate.split(' ')[1] + ' ' + newDate.split(' ')[3];
}

function checkDate(date)
{
    var splitted = date.split(' ');
    if(splitted.length != 3)
        return false;
    if(parseInt(splitted[0]) < 1 || parseInt(splitted[0]) > 31)
        return false;
    if(parseInt(splitted[2]) < 1970 || parseInt(splitted[2]) > 2100)
        return false;
    return in_array(splitted[1], ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']);
}

function in_array(value, array)
{
    for(var i = 0; i < array.length; i++)
    {
        if(array[i] == value) return true;
    }
    return false;
}

function findUsers() {
    $('#submitEdit').prop('disabled', true);
    var input_search = $("#user").val();
    if (input_search.length >= 2 && input_search.length < 30)
    {
        $.ajax({
            type: "POST",
            url: "search_account",
            contentType: "application/json",
            data: JSON.stringify({text: input_search}),
            dataType: "json",
            cache: false,
            success: function(data) {
                $("#block-search-result").show();
                $(".founded_users").remove();
                data.forEach(function (user) {
                    $("#list-search-result").append("<li id='FU" + user.id + "' class='founded_users' value='" + user.id + "'>" + user.login + "</li>");
                    $('#FU' + user.id).click(function () {
                        $('#user').prop('value', user.login);
                        $(".founded_users").remove();
                        $('#submitEdit').prop('disabled', false);
                    })
                });
            }
        });
    }else
    {
        $("#block-search-result").hide();
    }
}

function selectProcrastinatedTasks()
{
    $('.tabrow').each(function () {
        if($('#todo-' + $(this).prop('id') + '-status').text() == getStatus(3))
        {
            $(this).css({'background-color': 'gray'});
        }
        else if(Date.parse($("#todo-" + $(this).prop('id') + "-duedate").text()) - Date.parse(new Date()) < 0)
        {
            $(this).css({'background-color': 'tomato'});
        }
    })
}

function deleteFromBasket(id) {
    $.ajax({
        type: "POST",
        url: "/delete_from_basket",
        contentType: "application/json",
        data: {"id": id},
        dataType: "json",
        cache: false,
        success: function(data) {
            if(data.error === true) {
                $.msgGrowl({
                    type: 'error',
                    title: 'Basket',
                    text: data.text
                });
            }
            else {
                $.msgGrowl({
                    type: 'success',
                    title: 'Basket',
                    text: 'Item was successfully removed from your basket'
                });
                $('#bi_row_'+id).remove();
            }
        }
    });
}

function checkBasket() {
    $.ajax({
        type: "POST",
        url: "/check_for_order",
        contentType: "application/json",
        dataType: "json",
        success: function(data) {
            if(data.error === true) {
                $.msgGrowl({
                    type: 'error',
                    title: 'Creating the order',
                    text: data.text
                });
            }
            else {
                $('#make_order_popup').modal();
            }
        }
    });
}

function make_order() {
    $.ajax({
        type: "POST",
        url: "/order",
        contentType: "application/json",
        data: JSON.stringify({name: $("#input_name").val(), email: $("#input_email").val(), telephone: $("#input_telephone").val(),
            address: $("#input_address").val(), pay_type: $('input[name=pay_type]:checked').val() }),
        dataType: "json",
        success: function(data) {
            if(data.error === true) {
                $.msgGrowl({
                    type: 'error',
                    title: 'Creating the order',
                    text: data.text
                });
            }
            else {
                $('#make_order_popup').modal('hide');
                $.msgGrowl({
                    type: 'success',
                    title: 'Creating the order',
                    text: 'Order have been created. Please, wait a telephone call from our operator.'
                });
                $('.bi_row').remove();
            }
        }
    });
}