/*	Image Cross Fade Redux	Version 1.0	Last revision: 02.15.2006	steve@slayeroffice.com	Rewrite of old code found here: http://slayeroffice.com/code/imageCrossFade/index.html*/window.addEventListener?window.addEventListener('load',so_init,false):window.attachEvent('onload',so_init);var d=document, imgs = new Array(), zInterval = null, current=0, pause=false; prevFade=0; succFade=0;function so_init(){	if(!d.getElementById || !d.createElement)return;	css = d.createElement('link');	css.setAttribute('href','slideshow2.css');	css.setAttribute('rel','stylesheet');	css.setAttribute('type','text/css');	d.getElementsByTagName('head')[0].appendChild(css);	imgs = d.querySelectorAll('.imgSlide');	imgsNav = d.querySelectorAll('#navigatore>img[id^=nav]');	spans = d.getElementById('slideshow').getElementsByTagName('span');		for(i=1;i<imgs.length;i++) imgs[i].xOpacity = 0;		spans[0].style.display = 'block';	imgs[0].style.display = 'block';	d.getElementById('bPrev').addEventListener?d.getElementById('bPrev').addEventListener('click',so_xfade_prev,false):d.getElementById('bPrev').attachEvent('onclick',so_xfade_prev);	d.getElementById('bSucc').addEventListener?d.getElementById('bSucc').addEventListener('click',so_xfade_succ,false):d.getElementById('bSucc').attachEvent('onclick',so_xfade_succ);	$('span[id^=ex]').zoom();	$('#navigatore>img[id^=nav]').click(function(){var a = this.id.substring(3,this.id.length); so_xfade_i(parseInt(a))})	$('#thumbDiv>img').mouseover(function(){var a = this.id.substring(5,this.id.length); so_xfade_i(parseInt(a))});	}function so_xfade_succ(){	current = imgs[current+1]?current+1:0;	attivaImmagine();}function so_xfade_prev(){	current = imgs[current-1]?current-1:imgs.length-1;	attivaImmagine()}function so_xfade_i(i){	if (current==i)return;	current = i;	attivaImmagine();}function attivaImmagine(){	$("#slideshow>span").fadeOut("600");	var a = $("#slideshow>span");	$(a[current]).fadeIn(600);	attivaNavigatore();	}function attivaNavigatore(){	for (j=0;j<imgsNav.length;j++){		if (j==current){			imgsNav[j].src="pallinoPieno.png"		}else{ 			imgsNav[j].src="pallinoVuoto.png"		}			}}