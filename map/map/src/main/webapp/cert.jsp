<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>证书验证</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
	<style type="text/css">
	.menu li{
		display: inline-block;
	}
	</style>
  </head>
  <body>
  <form>
  
  	<div>
	<ul>
	<li><label>服务器</label>
	<select name="host" id="host">
	<option value="http://<%=request.getServerName()%>:<%=request.getServerPort()%><%=request.getContextPath() %>" selected>当前服务器地址</option>
	</select><input type='txt' id='realhost' >
	<li><label>接口名</label>
	<textarea id='api' cols="60"></textarea>

	</li>
	<li>
	<label>公钥证书路径</label>
	<input type='txt' id='publickeyFilePath'  name='publickeyFilePath' value="d:\x.pem" >
	</li>
	<li>
	<label>私钥证书路径</label>
	<input type='txt' id='privatekeyFilePath'  name='privatekeyFilePath' value="d:\x.pfx" >
	</li>
	<li>
	<label>私钥证书密码</label>
	<input type='txt' id='privatekeyPassword'  name='privatekeyPassword' value="123" >
	</li>
	<li><label>公钥证书中的公钥内容(base64)<input type='button' onclick='togetkey()' value='获取'> </label>
	<p>
	<textarea id='publickeyContentInPublicKeyCert'  name='publickeyContentInPublicKeyCert' cols="160" rows="4"></textarea>
	</li>
	<li><label>私钥证书中的公钥内容(base64)</label>
	<p>
	<textarea id='publickeyContentInPrivateKeyCert'  name='publickeyContentInPrivateKeyCert' cols="160" rows="4"></textarea>
	</li>
	<li><label>私钥证书中的私钥内容(base64)</label>
	<p>
	<textarea id='privatekeyContentInPrivateKeyCert'  name='privatekeyContentInPrivateKeyCert' cols="160" rows="7"></textarea>
	</li>
	<li><label>明文</label><textarea id="painttext"  name="painttext" cols="100" rows="2">wangyf</textarea></li>
	<li><label>加解密使用算法</label>
	<p>可选 RSA,RSA/NONE/PKCS1Padding,RSA/NONE/PKCS1Padding,RSA/ECB/NoPadding等</p>
	<input id="cipher_value" name="cipher_value" value="RSA"></></li>
	<li><label><input type='button' value='私钥加密' onclick='encyptWithPrivateKey()'></label>
	<textarea id="txt_encyptbyprivatekey" name="txt_encyptbyprivatekey" cols="100" rows="2"></textarea></li>
	<li><label><input  type='button' value='公钥解密'  onclick='decyptWithPublicKey()'></label>
	<textarea id="txt_decyptbypublickey" name="txt_decyptbypublickey" cols="100" rows="2"></textarea></li>
	
	
	
	
	<li><input type="button" value="提交" id="bt"></li>
	<li><label>响应</label><div id="resp"></div></li>
	</ul>
</ul>
</form>
</div> 
  </body>
    <script>
    var type='payload'
    
		$("#bt").click(function (){
			$("#resp").html("");
			var hostval = $("#realhost").val()
			if(hostval==''){
				hostval=$("#host").val()
			}
			var url =hostval+$("#api").val();
			var data = $("#param").val();
			if(type=='payload')
			{
				var vcookie = $('#cookie').val();
				if(vcookie!=''){
					try{
						vcookie = eval('('+vcookie+')');
						for(item in vcookie){
							$.cookie(item, vcookie[item]);
						}
					}
					catch(e){console.print(e)}
					
				}
				
				$.ajax({type:'POST',contentType:'application/json',url:url,data:data,
				success:function(resp){ $("#resp").html(resp);},dataType:"html"}
				);
			}
			else if(type=='cookie'){
				data = eval('('+data+')');
				for(item in data){
					$.cookie(item, data[item]);
				}
				$.ajax({type:'POST',contentType:'application/json',url:url,
					success:function(resp){ $("#resp").html(resp);},dataType:"html"}
					);
			}
			
		});
		
		function setbinding(api,json,cookiejson){
			$("#api").val(api)
			$("#param").val(json)
			$("#cookie").val(cookiejson)
			type='payload'
		}
		
		jQuery.cookie = function(name, value, options) {
			if (typeof value != 'undefined') {
			   options = options || {};
			   if (value === null) {
			    value = '';
			    options = $.extend({}, options);
			    options.expires = -1;
			   }
			   var expires = '';
			   if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
			    var date;
			    if (typeof options.expires == 'number') {
			     date = new Date();
			     date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
			    } else {
			     date = options.expires;
			    }
			    expires = '; expires=' + date.toUTCString();
			   }
			   var path = options.path ? '; path=' + (options.path) : '';
			   var domain = options.domain ? '; domain=' + (options.domain) : '';
			   var secure = options.secure ? '; secure' : '';
			   document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
			} else {
			   var cookieValue = null;
			   if (document.cookie && document.cookie != '') {
			    var cookies = document.cookie.split(';');
			    for (var i = 0; i < cookies.length; i++) {
			     var cookie = jQuery.trim(cookies[i]);
			     if (cookie.substring(0, name.length + 1) == (name + '=')) {
			      cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
			      break;
			     }
			    }
			   }
			   return cookieValue;
			}
			};
			
			function setbinding_cookie(api,json){
				$("#api").val(api)
				$("#param").val(json)
				type = 'cookie';
				
			}
			
			(function($){  
		        $.fn.serializeJson=function(){  
		            var serializeObj={};  
		            var array=this.serializeArray();  
		            var str=this.serialize();  
		            $(array).each(function(){  
		                if(serializeObj[this.name]){  
		                    if($.isArray(serializeObj[this.name])){  
		                        serializeObj[this.name].push(this.value);  
		                    }else{  
		                        serializeObj[this.name]=[serializeObj[this.name],this.value];  
		                    }  
		                }else{  
		                    serializeObj[this.name]=this.value;   
		                }  
		            });  
		            return serializeObj;  
		        };  
		    })(jQuery);
			
		
		function togetkey(){
			var hostval = $("#realhost").val()
			if(hostval==''){
				hostval=$("#host").val()
			}
			var url =hostval+"/cert/getKeyFromCert";
			$.ajax({type:'POST',url:url,data:$("form").serialize(),
				success:function(resp){ 
					$("#publickeyContentInPublicKeyCert").html(resp["keyfrompublickey"]);
					$("#publickeyContentInPrivateKeyCert").html(resp["publickeyfrompfx"]);
					$("#privatekeyContentInPrivateKeyCert").html(resp["privatekeyfrompfx"]);
				
				},dataType:"json"}
			);
			
		}
		
		function encyptWithPrivateKey(){
			var hostval = $("#realhost").val()
			if(hostval==''){
				hostval=$("#host").val()
			}
			var url =hostval+"/cert/encyptWithPrivateKey";
			$.ajax({type:'POST',url:url,data:$("form").serialize(),
				success:function(resp){ 
					$("#txt_encyptbyprivatekey").html(resp["txt_encyptbyprivatekey"]);
				},dataType:"json"}
			);
		}
		function decyptWithPublicKey(){
			var hostval = $("#realhost").val()
			if(hostval==''){
				hostval=$("#host").val()
			}
			var url =hostval+"/cert/decyptWithPublicKey";
			$.ajax({type:'POST',url:url,data:$("form").serialize(),
				success:function(resp){ 
					$("#txt_decyptbypublickey").html(resp["txt_decyptbypublickey"]);
				},dataType:"json"}
			);
		}
	</script>
  
</html>
