<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form id="myForm" action="#">
  <input name="name"/>
  <input name="age"/>
  <input type="submit"/>
</form>
<script src="http://cdn.static.runoob.com/libs/jquery/1.10.2/jquery.min.js"></script>
<script>
  (function($){
    $.fn.serializeJson=function(){
      var serializeObj={};
      $(this.serializeArray()).each(function(){
        serializeObj[this.name]=this.value;
      });
      return serializeObj;
    };
 
    $('#myForm').bind('submit',function(e){
      console.log($(this).serializeJson())
    })
  })
 
</script>
</body>
</html>

