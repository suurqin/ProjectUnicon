var BASE_URL = "http://39.106.138.102:8088";
function initData(){
	var arr =['1','2','3','4']
	for(var i = 0;i<arr.length;i++){  //循环LIST

		selectTab(arr[i]);
	}
}
function selectTab(cid){
		$("#tab_"+cid).empty();
		var type =$.base64.encode(1);
		var cidBase = $.base64.encode(cid);
		var params = {"type":type,"cid":cidBase}
		$.ajax({
				type : 'post',
				url : BASE_URL + '/api/common/P004',
				data : params,
				dataType : 'json',
				timeout : 10000,
				beforeSend: function(request) {
     			request.setRequestHeader("secret", 0);
				},
				success : function(data) {
					if (data.code == 0) {
						var list = data.data;
						
						if(cid==1){
							//总承包
							showzcb(list,cid);
						}else if(cid ==2){
							//监理资质
							showzcb(list,cid);
						}else if(cid==3){
							showzcb(list,cid);
						}else if(cid ==4){
							showrg(list,cid);
						}
								//勾选
					var check= $('input[name="fruit"]')
						check.on("click",function(){
							var lengthed=$("input[type='checkbox']:checked").length;
							var num=$(".j_infofoot p span").text(lengthed);
							//alert(num)
					
							if(lengthed>=16){
								$("input").not(":checked").attr("disabled",true)
							}
							else{
								$("input").not(":checked").attr("disabled",false)
							}
			})
					} else {
						alert(data.msg);
					}
					return false;
				}
			});
		
		}	
		function showzcb(list,cid){
						var html ='';
						for(var i = 1;i<list.length;i++){  //循环LIST
            	if(list[i].pid==cid){
            		if(i!=1){
            				html +='</tr>'; 
            		}
            		html +='<tr>';
            		html +='<th>'+list[i].name+'</th>'
            	}else{
            		html += '<td><label><input type="checkbox" value="'+list[i].categoryId+'" name="fruit"><i></i></label></td>'
         	 }   	                    
      }
       $("#tab_"+cid).append(html);	
	}
	function showrg(list,cid){
						var html ='';
						for(var i = 2;i<list.length;i++){ 
							html += '<label><input type="checkbox" value="'+list[i].categoryId+'" name="fruit"><i>'+list[i].name+'</i></label>' 
							if(i%3==0){
								html +='<br/>'
							}
         	 }  
         	  $("#tab_"+cid).append(html);		                    
      }
  function uploadData(){
  	var categoryIds = getCategoryIds();
  	alert(categoryIds)
  	native.getCategoryIds(categoryIds);
  	
  	}    
 //获取选中的id，然后拼接成字符串  
 function getCategoryIds(){
	var categoryIds = $("input[name='fruit']:checked");
	var categoryIds_ = "";
	$.each(categoryIds, function(index, categoryId){
		categoryIds_ += categoryId.value + ",";
	});

	if(categoryIds_.length>0){
		categoryIds_ = categoryIds_.substring(0, categoryIds_.length - 1);
	}
	return categoryIds_;
}