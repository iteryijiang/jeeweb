//input 框失去焦点后判断基本是否为空等
function inputDefocus(item){
    if($(item).val()==''){
        $(item).parent().parent().addClass('error')
    }
}