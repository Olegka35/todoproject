/**
 * Created by Олег on 09.06.2018.
 */
$(document).ready(function(){
    $('.delete_attr').click(function() {
        deleteAttr($(this).attr('id'));
    });
});


function add_param() {
    if($('#newparam').val().length == 0) console.log('Error');
    else {
        $.ajax({
            type: "POST",
            url: "/params/addparam",
            contentType: "application/json",
            data: JSON.stringify({name: $('#newparam').val()}),
            dataType: "json",
            cache: false,
            success: function(data) {
                if(data.error == true) {
                    $.msgGrowl({
                        type: 'error',
                        title: 'Adding',
                        text: data.text
                    });
                }
                else {
                    window.location.reload();
                }
            }
        });
    }
}

function deleteAttr(id)
{
    $.ajax({
        type: "POST",
        url: "/params/delete",
        contentType: "application/json",
        data: JSON.stringify({id: id}),
        dataType: "json",
        cache: false,
        success: function(data) {
            if(data.error == true){
                $.msgGrowl({
                    type: 'error',
                    title: 'Delete',
                    text: data.text
                });
            }
            else {
                $('#attr_' + id).remove();
                $.msgGrowl({
                    type: 'success',
                    title: 'Delete',
                    text: 'Attribute deleted succesfully!'
                });
            }
        }
    });
}