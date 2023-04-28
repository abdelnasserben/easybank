
$(function () {
    
    $("#transactType").change(function() {
        
        $(".tranferCards > .card").hide()
        var val = $(this).children(':selected').text();
        switch(val) {
            case "Payment":
                $("#paymentCard").show();
            break;
            case "Transfer":
                $("#transferCard").show();
            break;
            case "Deposit":
                $("#depositCard").show();
            break;
            case "Withdraw":
                $("#withdrawCard").show();
            break;
            default:
                $(".tranferCards > .card").hide()
        }
    });

});