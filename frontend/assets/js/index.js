$(document).ready(function(){

    $.ajax({
        url: "http://localhost:8080/api/list",
        headers:{
            "Access-Control-Allow-Origin":"http://localhost",
        },
        statusCode: {
            500: function(xhr){
                console.log("erro 500");
                return;
            },
            403: function(xhr){
                console.log("erro 403");
                return;
            }
        }

    }).then(function(data) {
        data.forEach(country=>{
            $('#countries').append('<option value=\"' + country + '\"> ' + country + '</option>')
        })
        
       
    });
    
});

$('#countries').change(function(){

    if($('#countries').find(":selected").text() != 'select the country'){
        $('#states').prop('disabled', false);
        $('#states').empty();
        $('#states').append('<option value="default">select the state</option>')
        $.ajax({
            url: "http://localhost:8080/api/list/" + $('#countries').find(":selected").text(),
            headers:{
                "Access-Control-Allow-Origin":"http://localhost",
            },
            statusCode: {
                500: function(xhr){
                    console.log("erro 500");
                    return;
                },
                403: function(xhr){
                    console.log("erro 403");
                    return;
                }
            }
    
        }).then(function(data) {
            data.forEach(state=>{
                $('#states').append('<option value=\"' + state + '\"> ' + state + '</option>')
            })
            
           
        });
    }
    

});

$('#states').change(function(){

    if($('#states').find(":selected").text() != 'select the state'){
        $('#cities').prop('disabled', false);
        $('#cities').empty();
        $('#cities').append('<option value="default">select the city</option>')
        $.ajax({
            url: "http://localhost:8080/api/list/" + $('#countries').find(":selected").text() + "/" + $('#states').find(":selected").text(),
            headers:{
                "Access-Control-Allow-Origin":"http://localhost",
            },
            statusCode: {
                500: function(xhr){
                    console.log("erro 500");
                    return;
                },
                403: function(xhr){
                    console.log("erro 403");
                    return;
                }
            }
    
        }).then(function(data) {
            console.log(data)
            data.forEach(city=>{
                $('#cities').append('<option value=\"' + city + '\"> ' + city + '</option>')
            })
            
           
        });
    }
    

});

$('#searchButton').click(function(){
    $.ajax({
        url: "http://localhost:8080/api/" + $('#countries').find(":selected").text() + "/" + $('#states').find(":selected").text() + "/" + $('#cities').find(":selected").text(),
        headers:{
            "Access-Control-Allow-Origin":"http://localhost",
        },
        statusCode: {
            500: function(xhr){
                console.log("erro 500");
                return;
            },
            403: function(xhr){
                console.log("erro 403");
                return;
            }
        }

    }).then(function(data) {
        console.log(data)
        
        
       
    });
});

