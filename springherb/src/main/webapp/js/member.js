/**
 * js/member.js
 */
$(document).ready(function(){
	$('#email2').change(function(){
		if($(this).val()=='etc'){
			$('#email3').css('visibility','visible');
			$('#email3').val('');
			$('#email3').focus();
		}else{
			$('#email3').css('visibility','hidden');
		}
	});

	$('#btnZipcode').click(function(){
		//우편번호 찾기
		window.open('/herb/zipcode/zipcode.do', 'zip', 
		'width=500,height=550,left=0,top=0,location=yes,resizable=yes');
	});
});

function validate_phoneno(ph){
	var pattern=new RegExp(/^[0-9]*$/g);
	return pattern.test(ph); //정규식과 일치하면 true
	/*  정규식  /^[0-9]*$/g
			0 에서 9사이의 숫자로 시작하거나 끝나야 한다는 의미 (^는 시작, $는 끝을 의미)
			닫기 대괄호(]) 뒤의 * 기호는 0번 이상 반복
	 */
}

function validate_userid(uid){
	var pattern= new RegExp(/^[a-zA-Z0-9_]+$/g);
	//var pattern= new RegExp(/^[a-zA-Z0-9_]{4,10}$/g);
	//4~10자로 작성해야 함
	return pattern.test(uid);
	/*
		    	정규식  /^[a-zA-Z0-9_]+$/g
			a에서 z 사이의 문자, A~Z사이의 문자, 0 에서 9사이의 숫자나 _로 시작하거나 끝나야 한다는 의미
			닫기 대괄호(]) 뒤의 + 기호는 이 패턴이 한 번 또는 그 이상 반복된다는 의미
	 */
}
