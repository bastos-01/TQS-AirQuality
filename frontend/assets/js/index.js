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
        if(!$.trim(data)){
            alert("City not available at this moment");
        }
        else{
            data.forEach(country=>{
            $('#countries').append('<option value=\"' + country + '\"> ' + country + '</option>')
        })
        }
        
        
       
    });

    $.ajax({
        url: "http://localhost:8080/api/cache",
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
        $('#hits').text(data.hits);
        $('#misses').text(data.misses);
        $('#requests').text(data.requests);
        
        
       
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
            if(!$.trim(data)){
                alert("City not available at this moment");
            }
            else{
                data.forEach(state=>{
                $('#states').append('<option value=\"' + state + '\"> ' + state + '</option>')
            })
            }
            
            
           
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
            console.log("olaaaaaa");
            if(!$.trim(data)){
                alert("City not available at this moment");
            }
            else{
                data.forEach(city=>{
                $('#cities').append('<option value=\"' + city + '\"> ' + city + '</option>')
            })
            }
           
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
        console.log(data);
        $('#cityfill').text(data.name + ", " + data.state + ", " + data.country);
        $('#latfill').text("Latitude: " + data.latitude);
        $('#longfill').text("Longitude: " + data.longitude);
        $('#tpfill').text(data.weather.temperature + " ºC");
        $('#prfill').text(data.weather.pressure + " hPa");
        $('#hufill').text(data.weather.humidity + " %");
        $('#wsfill').text(data.weather.windSpeed + " m/s");
        $('#aqius').text(data.pollution.aqiUs);
        $('#aqicn').text(data.pollution.aqiCn);
        $('#mainus').text(data.pollution.mainPollUs);
        $('#maincn').text(data.pollution.mainPollCn);

        
       
    });
});

$('#updateButton').click(function(){
    $.ajax({
        url: "http://localhost:8080/api/cache",
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
        $('#hits').text(data.hits);
        $('#misses').text(data.misses);
        $('#requests').text(data.requests);
        
        
       
    });
});

