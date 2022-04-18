;
if (!$.lbeen){
    $.lbeen = {};
}
$.lbeen = {
    confirm : function (opts) {
        var opt;
        if (typeof opts == 'string'){
            opt = {content : opts};
        } else if (opts){
            opt = opts;
        } else {
            opt = {};
        }
        var option = {
            type : 'confirm',
            title : opt.title || '提示',
            okFun : opt.okFun,
            noFun : opt.noFun,
            ok : opt.ok || '确认',
            no : opt.no || '取消',
            width : opt.width || 400,
            height : opt.height || 200,
            url : opt.url
        };
        if (opt.content){
            option['content'] = option.content;
            lbeenAlert(option);
        } else if (opt.url){
            $.get(opt.url, null, function (data) {
                option['content'] = data;
                lbeenAlert(option);
            }, 'html');
        }
    },
    alert : function (opts) {
        var opt;
        if (typeof opts == 'string'){
            opt = {content : opts};
        } else if (opts){
            opt = opts;
        } else {
            opt = {};
        }
        var option = {
            type : 'alert',
            title : opt.title || '提示',
            okFun : opt.okFun,
            ok :'确认',
            width : opt.width || 300,
            height : opt.height || 200,
            content : opt.content || ""
        };
        lbeenAlert(option);
    },
    content : function(opts){
        var opt;
        if (typeof opts == 'string'){
            opt = {content : opts};
        } else if (opts){
            opt = opts;
        } else {
            opt = {};
        }
        var option = {
            type : 'confirm',
            title : opt.title || '提示',
            okFun : opt.okFun,
            noFun : opt.noFun,
            ok : opt.ok || '确认',
            no : opt.no || '取消',
            width : opt.width || 400,
            height : opt.height || 200,
            url : opt.url
        };
        if (option.content){
            option['content'] = option.content;
            lbeenShowContent(option);
        } else if (option.url){
            $.get(option.url, null, function (data) {
                option['content'] = data;
                lbeenShowContent(option);
            }, 'html');
        }
    }
};

function lbeenAlert(opt) {
    var dialogId;
    var bHtml;
    if ('alert' == opt.type){
        dialogId = 'lbeenAlert';
        bHtml = '<button role="close" type="button" class="btn btn-default" data-dismiss="modal">' + opt.ok + '</button>'
    } else {
        dialogId = 'lbeenConfirm';
        bHtml = '<button role="close" type="button" class="btn btn-default" data-dismiss="modal">' + opt.no + '</button>'
            + '<button role="ok" type="button" class="btn btn-primary">' + opt.ok + '</button>'

    }
    var $dialog = $('#' + dialogId);
    $dialog.remove();
    var html =
        '<div class="modal fade" id="' + dialogId + '" tabindex="-1" role="dialog">' +
            '<div class="modal-dialog" role="document" style="width:' + opt.width + 'px;height:' + opt.width + 'px">' +
                '<div class="modal-content">' +
                    '<div class="modal-header">' +
                        '<h4 class="modal-title">' + opt.title + '</h4>' +
                    '</div>' +
                    '<div class="modal-body">' + opt.content + '</div>' +
                    '<div class="modal-footer">' + bHtml + '</div>' +
                '</div>' +
            '</div>' +
        '</div>';
    $('body').prepend(html);
    $dialog = $('#' + dialogId);
    if (opt.okFun) {
        $dialog.find('button[role=ok]').on("click", function () {
            opt.okFun();
        });
    }
    if (opt.noFun) {
        $dialog.find('button[role=close]').on("click", function () {
            opt.noFun();
        });
    }
    $dialog.modal({backdrop:'static'});
}

function lbeenShowContent(opt) {
    var $dialog = $('#lbeenContent');
    $dialog.remove();
    var html =
        '<div class="modal fade" id="lbeenContent" tabindex="-1" role="dialog">' +
            '<div class="modal-dialog" role="document" style="width:' + opt.width + 'px;height:' + opt.width + 'px">' +
                '<div class="modal-content">' +
                    '<div class="modal-header">' +
                        '<button type="button" class="close" data-dismiss="modal" aria-label="Close">' +
                            '<span aria-hidden="true">&times;</span>' +
                        '</button>' +
                        '<h4 class="modal-title">' + opt.title + '</h4>' +
                    '</div>' +
                    '<div class="modal-body">' + opt.content + '</div>' +
            '</div>' +
        '</div>';
    $('body').prepend(html);
    $dialog = $('#lbeenContent');
    $dialog.modal({backdrop:'static'});
}