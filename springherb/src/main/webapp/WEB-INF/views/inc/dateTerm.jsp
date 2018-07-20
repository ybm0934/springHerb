<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link href="<c:url value='/css/jquery-ui-base.min.css'/>" type="text/css" rel="stylesheet">
<script type="text/javascript" src="<c:url value='/js/jquery-ui.min.js'/>"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$.setToday();
		
		$('#btWeek').click(function(){
			$.setTerm('d', 7);	//7일전
		});
		$('#btMonth1').click(function(){
			$.setTerm('m', 1);	//1개월전	
		});
		$('#btMonth3').click(function(){
			$.setTerm('m', 3);	//3개월전
		});
		
		$('form[name=frm1]').find('input[type=text]').datepicker({
			dateFormat:'yy-mm-dd',
			changeYear:true,
			dayNamesMin:['일','월','화','수','목','금','토'],
			monthNames:['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','1월','12월']
		});
		
	});
	
	$.setTerm=function(type, term){
		//var d = new Date(년,월,일);
		//종료일 구하기
		var arr = $('#endDay').val().split('-');	//2018-07-09
		var d = new Date(arr[0], arr[1]-1, arr[2]);	//new Date(2018,6,9);
		
		//종료일 기준 7일전, 1개월전, 3개월전
		if(type=='d'){
			d.setDate(d.getDate()-term);
		}else{
			d.setMonth(d.getMonth()-term);
		}
		
		$('#startDay').val($.findDate(d));
	
	}
	
	$.setToday=function(){
		//날짜가 지정되지 않은 경우, 현재일자 셋팅
		if($('#startDay').val() == "" || $('#endDay').val() == ""){
			var d = new Date();
			var today = $.findDate(d);
			$('#startDay').val(today);
			$('#endDay').val(today);
		}
	}
	
	$.findDate=function(d){
		return d.getFullYear() + "-" + $.formatDate(d.getMonth()+1) + "-" + $.formatDate(d.getDate());
		// 월 단위의 경우 0부터 시작되기 때문에 +1을 해줘야 됩니다.
	}
	
	$.formatDate=function(day){
		if(day < 10){
			return "0" + day;	// 01월 ~ 09월
		}else{
			return day;	// 10월 ~ 12월
		}
	}
</script>

    <!-- 조회기간 include -->
	조회기간
	<input type="button" value="1주일" id="btWeek">
	<input type="button" value="1개월" id="btMonth1">
	<input type="button" value="3개월" id="btMonth3">
		
	<input type="text" name="startDay" id="startDay" value="${param.startDay }"> 
	~ 
	<input type="text" name="endDay" id="endDay" value="${param.endDay }">

	
	