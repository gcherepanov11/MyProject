var scr = document.getElementById('scroll1');
var scr2 = document.getElementById('scroll2');
var outTime1 = document.getElementById('outTime1');
var outTime2 = document.getElementById('outTime2');
var scrollLine1 = document.getElementById('scrollLine1');
var scrollLine2 = document.getElementById('scrollLine2');
let startArr = [], endArr = [];
let stepTimeToShift = 0;


// передвинуть под координаты курсора
// и сдвинуть на половину ширины/высоты для центрирования
function moveAt(ptr, e, outTime, scrollLine, tableTime, numTime) {
	let dataX;
	if(e.pageX > canvasW + canvasLeft){
		ptr.style.left = canvasW + canvasLeft - ptr.offsetWidth / 2;// + 'px';scrollLine1
		outTime.style.left = canvasW + canvasLeft - 80;
		scrollLine.style.left = canvasW + canvasLeft - scrollLine.offsetWidth / 2;;
		dataX = canvXtoAbsX(canvasW + canvasLeft);
	}
	else if(e.pageX < canvasLeft){
		ptr.style.left = canvasLeft - ptr.offsetWidth / 2;// + 'px';scrollLine1
		outTime.style.left = canvasLeft;
		scrollLine.style.left = canvasLeft - scrollLine.offsetWidth / 2;;
		dataX = canvXtoAbsX(canvasLeft);
	}
	else{
		ptr.style.left = e.pageX - ptr.offsetWidth / 2;// + 'px';scrollLine1
		if(e.pageX < 40)outTime.style.left = 0;
		outTime.style.left = ptr.style.left;
		scrollLine.style.left = e.pageX - scrollLine.offsetWidth / 2;;
		dataX = canvXtoAbsX(e.pageX);
	}
	
	let dtObj = dateTimeFormat(dataX);
	outTime.innerHTML = dtObj;
	document.getElementById(tableTime).innerHTML = dtObj;
	
	let j = trends[0].p.getTrendPiece(dataX);
	for(let i = 0; i<trendList.length; i++)if(trendList[i] > 0){
		if(trends[i].p.flagBool) document.getElementById("val"+i+"_"+ numTime).innerHTML = trends[i].p.getValTrendFromInd(j, dataX);
		else document.getElementById("val"+i+"_"+ numTime).innerHTML = +formatNum( trends[i].p.getValTrendFromInd(j, dataX),4);
	}

	getNumberMsgByDT(dataX, numTime);
}

function setValOfNewTime(outTime, tableTime, numTime, dataX) {
	let dtObj = dateTimeFormat(dataX);
	outTime.innerHTML = dtObj;
	document.getElementById(tableTime).innerHTML = dtObj;
	for(let i = 0; i<trendList.length; i++)if(trendList[i] > 0){
		if(trends[i].p.flagBool) document.getElementById("val"+i+"_"+ numTime).innerHTML = trends[i].p.getValTrend(dataX);
		else document.getElementById("val"+i+"_"+ numTime).innerHTML = +formatNum( trends[i].p.getValTrend(dataX),4);
	}
}

function narrow(){
	if(startArr.length){
		startTime = startArr.pop();
		  endTime =   endArr.pop();
		reCreateTrends();
		
		scr.style.left = canvasLeft;
		outTime1.style.left = canvasLeft;
		scrollLine1.style.left = canvasLeft;
		
		scr2.style.left = canvasLeft + canvasW;
		outTime2.style.left = canvasLeft + canvasW - 80;
		scrollLine2.style.left = canvasLeft + canvasW - 1;
		setValOfNewTime(outTime1,'tableTime1', "1",startTime);
		setValOfNewTime(outTime2,'tableTime2', "2",endTime);
	}
}
function stretch(){
	let tmp1 = dateConvert(outTime1.innerHTML);
	let tmp2 = dateConvert(outTime2.innerHTML);
	if(tmp1 > tmp2){
		let tmp = tmp1;
		tmp1 = tmp2;
		tmp2 = tmp;
	}
	tmp1 = Math.floor(tmp1/1000) * 1000;
	tmp2 =  Math.ceil(tmp2/1000) * 1000;
	
	if(startTime == tmp1 && endTime == tmp2) return;
	
	startArr.push(startTime);
	  endArr.push(  endTime);
	startTime = tmp1;
	endTime   = tmp2;
	reCreateTrends();
	startSetTimeScrollings();
}

function startSetTimeScrollings(){
	scr.style.left = canvasLeft;
	outTime1.style.left = canvasLeft;
	scrollLine1.style.left = canvasLeft;
	scr2.style.left = canvasLeft + canvasW;
	outTime2.style.left = canvasLeft + canvasW - 80;
	scrollLine2.style.left = canvasLeft + canvasW - 1;
	setValOfNewTime(outTime1,'tableTime1', "1",startTime);
	setValOfNewTime(outTime2,'tableTime2', "2",endTime);	
}

function setStandartTimeStep(){
	if(document.getElementById('timeList')) return;
	let dt = document.getElementById('dt');
	let timeList = document.createElement("div");
	timeList.style= "position:absolute; border: 3px double blue; z-index:100;"
	timeList.style.left = dt.offsetLeft - 3;
	timeList.style.top = dt.offsetTop + dt.offsetHeight;
	timeList.style.width = dt.offsetWidth;
	timeList.id = "timeList";
	timeList.innerHTML = "<input type=\"button\" id=\"1ms\" onclick=\"setTimeStep(this)\"  value=\"1ms\" style=\"font-weight:bold; width:" + dt.offsetWidth + "\">" + 
						 "<input type=\"button\" id=\"10ms\" onclick=\"setTimeStep(this)\"  value=\"10ms\" style=\"font-weight:bold; width:" + dt.offsetWidth + "\">" + 
						 "<input type=\"button\" id=\"1s\" onclick=\"setTimeStep(this)\"  value=\"1s\" style=\"font-weight:bold; width:" + dt.offsetWidth + "\">" + 
						 "<input type=\"button\" id=\"10s\" onclick=\"setTimeStep(this)\"  value=\"10s\" style=\"font-weight:bold; width:" + dt.offsetWidth + "\">" + 
						 "<input type=\"button\" id=\"30s\" onclick=\"setTimeStep(this)\"  value=\"30s\" style=\"font-weight:bold; width:" + dt.offsetWidth + "\">" + 
						 "<input type=\"button\" id=\"1m\" onclick=\"setTimeStep(this)\"  value=\"1m\" style=\"font-weight:bold; width:" + dt.offsetWidth + "\">" + 
						 "<input type=\"button\" id=\"10m\" onclick=\"setTimeStep(this)\"  value=\"10m\" style=\"font-weight:bold; width:" + dt.offsetWidth + "\">" + 
						 "<input type=\"button\" id=\"30m\" onclick=\"setTimeStep(this)\"  value=\"30m\" style=\"font-weight:bold; width:" + dt.offsetWidth + "\">" + 
						 "<input type=\"button\" id=\"1h\" onclick=\"setTimeStep(this)\"  value=\"1h\" style=\"font-weight:bold; width:" + dt.offsetWidth + "\">" + 
						 "<input type=\"button\" id=\"6h\" onclick=\"setTimeStep(this)\"  value=\"6h\" style=\"font-weight:bold; width:" + dt.offsetWidth + "\">" + 
						 "<input type=\"button\" id=\"8h\" onclick=\"setTimeStep(this)\"  value=\"8h\" style=\"font-weight:bold; width:" + dt.offsetWidth + "\">" + 
						 "<input type=\"button\" id=\"12h\" onclick=\"setTimeStep(this)\"  value=\"12h\" style=\"font-weight:bold; width:" + dt.offsetWidth + "\">" + 
						 "<input type=\"button\" id=\"24h\" onclick=\"setTimeStep(this)\"  value=\"24h\" style=\"font-weight:bold; width:" + dt.offsetWidth + "\">" + 
						 "<input type=\"button\" id=\"00h00m00s000ms\" onclick=\"setTimeStep(this)\"  value=\"0h00m00s000ms\" style=\"font-weight:bold; width:" + dt.offsetWidth + "\">"+
						 "<input type=\"button\" id=\"Cancel\" onclick=\"setTimeStep(this)\"  value=\"Возврат\" style=\"font-weight:bold; background-color:#FF4444; width:" + dt.offsetWidth + "\">";
	document.body.append(timeList);
}

function setTimeStep(obj){
	if(obj.id != "Cancel"){
		document.getElementById('dt').value = obj.id;
		stepTimeToShift = getDeltaT(obj.id);
	}
	obj.parentElement.remove();
}

function shiftLeft(){
	if(!startArr.length){
		alert("График досиг максимального сдвига"); 
		return;
	}
	if(stepTimeToShift <= 0){
		setStandartTimeStep();
		return;
	}
	let dt = stepTimeToShift;
	if(startTime - startArr[0] < dt) dt = startTime - startArr[0];
	startTime = startTime - dt;
	endTime = endTime - dt;
	reCreateTrends();
	setValOfNewTime(outTime1,'tableTime1', "1",dateConvert(outTime1.innerHTML)-dt);
	setValOfNewTime(outTime2,'tableTime2', "2",dateConvert(outTime2.innerHTML)-dt);
}
function shiftRight(){
	if(!startArr.length){
		alert("График досиг максимального сдвига"); 
		return;
	}
	if(stepTimeToShift <= 0){
		setStandartTimeStep();
		return;
	}
	let dt = stepTimeToShift;
	if(endArr[0] - endTime < dt) dt = endArr[0] - endTime;
	startTime = startTime + dt;
	endTime = endTime + dt;
	reCreateTrends();
	setValOfNewTime(outTime1,'tableTime1', "1",dateConvert(outTime1.innerHTML)+dt);
	setValOfNewTime(outTime2,'tableTime2', "2",dateConvert(outTime2.innerHTML)+dt);
}
	
scr.onmousedown = function(e) { // 1. отследить нажатие
    document.onmousemove = function(e) {
		moveAt(scr, e, outTime1, scrollLine1, 'tableTime1', "1");
    }
	//отследить окончание переноса
	scr.onmouseup = function() {
		document.onmousemove = null;
		scr.onmouseup = null;
	}
}
scr2.onmousedown = function(e) { // 1. отследить нажатие
    document.onmousemove = function(e) {
		moveAt(scr2, e, outTime2, scrollLine2, 'tableTime2', "2");
    }
	//отследить окончание переноса
	scr2.onmouseup = function() {
		document.onmousemove = null;
		scr2.onmouseup = null;
	}
}
