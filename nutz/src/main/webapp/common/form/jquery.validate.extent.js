/**
 * Created by lbeen on 2017/5/21.
 */
$(function(){
    $.metadata.setType("attr", "data-validate");
    $('form[data-role="form"]').each(function () {
        $(this).validate({
            debug: true,
            submitHandler: function(form){
                var $from = $(form);
                var formOption = {};
                var beforesubmitStr = $from.data('beforesubmit');
                if (beforesubmitStr){
                    formOption['beforeSubmit'] = beforesubmitStr;
                }
                var afterSubmitStr = $from.data('aftersubmit');
                if (afterSubmitStr){
                    formOption['success'] = function (data) {
                        eval(afterSubmitStr).apply(this,[data])
                    };
                }

                $(form).ajaxSubmit(formOption);
            }
        });
    });


    $.validator.addMethod("intOrEnglish", function(value, element,param){
        return this.optional(element) || /^[\da-zA-Z]*$/g.test( value );
    }, '只可以输入数字或字母');
});