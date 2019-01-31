<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>RedMQ Manager</title>

    <!-- Bootstrap -->
    <link href="../statics/vendors/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="../statics/vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <!-- NProgress -->
    <link href="../statics/vendors/nprogress/nprogress.css" rel="stylesheet">
    <!-- iCheck -->
   <link href="../statics/vendors/iCheck/skins/flat/green.css" rel="stylesheet">

    <!-- Custom Theme Style -->
    <link href="../statics/build/css/custom.min.css" rel="stylesheet">
  </head>

  <body class="nav-md">
    <div class="container body">
      <div class="main_container">
        <jsp:include page="nav.jsp" />
        <!-- page content -->
        <div class="right_col" role="main">
      		<div class="row">
              <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                  <div class="x_title">
                    <h2>已注册的主题</h2>
                    <div class="clearfix"></div>
                  </div>
                  <div class="x_content">
                    <table class="table">
                      <thead>
                        <tr>
                          <th>#</th>
                          <th>Topic Name</th>
                          <th>Action</th>
                        </tr>
                      </thead>
                      <tbody>
                      </tbody>
                    </table>

                  </div>
                </div>
              </div>
              </div>
        </div>
        <!-- /page content -->
      </div>
    </div>

    <!-- jQuery -->
    <script src="../statics/vendors/jquery/dist/jquery.min.js"></script>
    <!-- Bootstrap -->
    <script src="../statics/vendors/bootstrap/dist/js/bootstrap.min.js"></script>
    <!-- FastClick -->
    <script src="../statics/vendors/fastclick/lib/fastclick.js"></script>
    <!-- NProgress -->
    <script src="../statics/vendors/nprogress/nprogress.js"></script>
    <!-- iCheck -->
    <script src="../statics/vendors/iCheck/icheck.min.js"></script>

    <!-- Custom Theme Scripts -->
    <script src="../statics/build/js/custom.min.js"></script>
    
    <script type="text/javascript">
    		$().ready(function(){
    			$.get('/api/getAllTopics',function(result){
    				if(result.success){
    					var html = '';
    					for(var i=0;i<result.data.length;i++){
    						html += '<tr>'
    	                          +'<th scope="row">'+(i+1)+'</th>'
    	                          +'<td>'+result.data[i]+'</td>'
    	                          +'<td><button data-topic="'+result.data[i]+'" class="getTopicGroups">查看topic下的分组</button></td>'
    	                          +'</tr>'
    					}
    					$('.right_col .table tbody').html(html);
    					initClickEvent();
    				}
    			});
    			
    			function initClickEvent(){
    				$('.getTopicGroups').on('click',function(){
    					$.get('/api/getTopicGroups/'+$(this).attr('data-topic'),function(result){
        					if(result.success){
        						if(result.data != null && result.data.length > 0){
        							alert(result.data);	
        						}else{
								alert('该topic下没有注册消费者分组');    							
        						}
        					}else{
        						alert(result.msg)
        					}
        				})
    				});
    			}
    			
    		});
    </script>
  </body>
</html>