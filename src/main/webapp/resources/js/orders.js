
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
