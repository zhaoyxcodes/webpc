<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html class="no-js">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="description" content="">
  <meta name="keywords" content="">
  <meta name="viewport"
        content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <title>Tamen</title>
 
    <!-- Set render engine for 360 browser -->
    <meta name="renderer" content="webkit">
 
    <!-- No Baidu Siteapp-->
    <meta http-equiv="Cache-Control" content="no-siteapp" />
 
    <link rel="icon" type="image/png" href="assets/i/favicon.png">
 
    <!-- Add to homescreen for Chrome on Android -->
    <meta name="mobile-web-app-capable" content="yes">
    <link rel="icon" sizes="192x192" href="assets/i/app-icon72x72@2x.png">
 
    <!-- Add to homescreen for Safari on iOS -->
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-title" content="Amaze UI" />
    <link rel="apple-touch-icon-precomposed" href="assets/i/app-icon72x72@2x.png">
 
    <!-- Tile icon for Win8 (144x144 + tile color) -->
    <meta name="msapplication-TileImage" content="assets/i/app-icon72x72@2x.png">
    <meta name="msapplication-TileColor" content="#0e90d2">
  
  <meta http-equiv="Cache-Control" content="no-siteapp"/>
  <link rel="stylesheet" href="css/amazeui.min.css">
  <link rel="stylesheet" href="css/app.css">
  <script type="text/javascript"> var webContext="${pageContext.request.contextPath}"</script>
</head>
<body>
<div class="am-g">
	<!-- 头部 -->
	<header data-am-widget="header"
          class="myAppHead am-header am-header-default">
      <div class="am-header-left am-header-nav">
          <a  href="#doc-oc-demo2" data-am-offcanvas="{target: '#doc-oc-demo2', effect: 'push'}">

                <i class="am-header-icon am-icon-bars"></i>
          </a>
      </div>

      <h1 class="am-header-title">
          <a href="#title-link" class="">
            Tamen
          </a>
      </h1>

      <div class="am-header-right am-header-nav">
          <a href="#right-link" class="">

                
          </a>
      </div>
  	</header>
  	<!-- 栏目切换 -->
	   <div data-am-widget="tabs"
       class="myAppTab am-tabs am-tabs-d2"
        >
      <ul class="am-tabs-nav am-cf">
          <li class="am-active"><a href="[data-tab-panel-0]">精选</a></li>
          <li class=""><a href="[data-tab-panel-1]">他们</a></li>
          <li class=""><a href="[data-tab-panel-2]">怀旧照</a></li>
      </ul>
      <div class="am-tabs-bd">
          <div data-tab-panel-0 class="myAppTabPanel am-tab-panel am-active">
				<div data-am-widget="slider" class="myAppSlider am-slider am-slider-a1" data-am-slider='{"directionNav":false}' >
					<ul class="am-slides">
					<li>
					<img src="img/a1.jpg">

					</li>
					<li>
					<img src="img/a2.jpg">
					</li>
					</ul>
				</div>
					<!-- 新闻列表 -->
		  <div data-am-widget="list_news" class="myAppNews am-list-news am-list-news-default" >



  <div class="am-list-news-bd">
  <ul class="am-list">
    
    
    
    
     <!--缩略图在标题右边-->
      <li class="am-g am-list-item-desced am-list-item-thumbed am-list-item-thumb-right">
        <div class=" am-u-sm-8 am-list-main">
            <h3 class="am-list-item-hd"><a href="###" class="">秋天屋檐下的红叶</a></h3>


            <div class="am-list-item-text">文章详细内容 </div>

        </div>
          <div class="am-u-sm-4 am-list-thumb">
            <a href="###" class="">
              <img src="img/b1.jpg"/>
            </a>
          </div>
      </li>
      <li class="am-g am-list-item-desced am-list-item-thumbed am-list-item-thumb-right">
        <div class=" am-u-sm-8 am-list-main">
            <h3 class="am-list-item-hd"><a href="###" class="">我最喜欢的一张画</a></h3>


            <div class="am-list-item-text">你会喜欢吗？</div>

        </div>
          <div class="am-u-sm-4 am-list-thumb">
            <a href="###" class="">
              <img src="img/b2.jpg" alt="我最喜欢的一张画"/>
            </a>
          </div>
      </li>
      <li class="am-g am-list-item-desced">
        <div class=" am-list-main">
            <h3 class="am-list-item-hd"><a href="###" class="">有趣的照片只为有趣的人</a></h3>
				  <ul data-am-widget="gallery" class="myAppGallery am-gallery am-avg-sm-3
  am-avg-md-3 am-avg-lg-3 am-gallery-default" data-am-gallery="{ pureview: true }" >
      <li>
        <div class="am-gallery-item">
            <a href="###" class="">
              <img src="img/a5.jpg" alt="远方 有一个地方 那里种有我们的梦想"/>
               
            </a>
        </div>
      </li>
      <li>
        <div class="am-gallery-item">
            <a href="###" class="">
              <img  src="img/a4.jpg"  alt="某天 也许会相遇 相遇在这个好地方"/>
               
            </a>
        </div>
      </li>
      <li>
        <div class="am-gallery-item">
            <a href="###" class="">
              <img  src="img/a8.jpg"  alt="不要太担心 只因为我相信"/>
               
            </a>
        </div>
      </li>

  </ul>


            <div class="am-list-item-text">有趣的照片详细描述  </div>

        </div>
      </li>
       <li class="am-g am-list-item-desced am-list-item-thumbed am-list-item-thumb-right">
        <div class=" am-u-sm-8 am-list-main">
            <h3 class="am-list-item-hd"><a href="###" class="">我最喜欢的一张画</a></h3>


            <div class="am-list-item-text">你是否喜欢</div>

        </div>
          <div class="am-u-sm-4 am-list-thumb">
            <a href="###" class="">
              <img src="img/b4.jpg" alt="我最喜欢的一张画"/>
            </a>
          </div>
      </li>
        
    </ul>
  </div>

    </div>

          </div>
          <div data-tab-panel-1 class="myAppTabPanel am-tab-panel ">
			
            <div class="myAppImgNews">
                <div class="myAppImgNewsList">
                  <div class="myAppImgNewsBlock">
                    <img src="img/a2.jpg"alt="">
                    <div class="myAppImgNewsTitle">
                      1940年的一张照片，一直保持到现在
                    </div>
                    <div class="myAppImgNewsUser">
                      <div class="myAppImgNewsIco"  onclick="javascript:location.href='page.jsp'">
                        <img src="img/c1.jpg">
                      </div>
                      <div class="myAppImgNewsName"  onclick="javascript:location.href='page.jsp'">
                        Dramos
                      </div>
                      <div class="myAppImgNewsTiem">
                        ToDay At 12:35
                      </div>
                    </div>
                  </div>

                  <div class="myAppImgNewsBlock">
                    <img src="img/a3.jpg"alt="">
                    <div class="myAppImgNewsTitle">
                     2017年冬天中午的冬梅
                    </div>
                    <div class="myAppImgNewsUser">
                      <div class="myAppImgNewsIco"  onclick="javascript:location.href='page.jsp'">
                        <img src="img/c2.jpg">
                      </div>
                      <div class="myAppImgNewsName"  onclick="javascript:location.href='page.jsp'">
                        niceguy
                      </div>
                      <div class="myAppImgNewsTiem">
                        ToDay At 12:35
                      </div>
                    </div>
                  </div>

                  <div class="myAppImgNewsBlock">
                    <img src="img/a4.jpg"alt="">
                    <div class="myAppImgNewsTitle">
                      家后院跑进来的小猫
                    </div>
                    <div class="myAppImgNewsUser">
                      <div class="myAppImgNewsIco"  onclick="javascript:location.href='page.jsp'">
                        <img src="img/c3.jpg">
                      </div>
                      <div class="myAppImgNewsName"  onclick="javascript:location.href='page.jsp'">
                        designaddict
                      </div>
                      <div class="myAppImgNewsTiem">
                        ToDay At 12:35
                      </div>
                    </div>
                  </div>

                  <div class="myAppImgNewsBlock">
                    <img src="img/a5.jpg"alt="">
                    <div class="myAppImgNewsTitle">
                      秋季也是一个温暖的季节
                    </div>
                    <div class="myAppImgNewsUser">
                      <div class="myAppImgNewsIco">
                        <img src="img/c4.jpg">
                      </div>
                      <div class="myAppImgNewsName">
                        Dramos
                      </div>
                      <div class="myAppImgNewsTiem">
                        ToDay At 12:35
                      </div>
                    </div>
                  </div>

                  <div class="myAppImgNewsBlock">
                    <img src="img/a2.jpg"alt="">
                    <div class="myAppImgNewsTitle">
                    猫咪的洞察力
                    </div>
                    <div class="myAppImgNewsUser">
                      <div class="myAppImgNewsIco">
                        <img src="img/c1.jpg">
                      </div>
                      <div class="myAppImgNewsName">
                        Dramos
                      </div>
                      <div class="myAppImgNewsTiem">
                        ToDay At 12:35
                      </div>
                    </div>
                  </div>
                </div>
            </div>

          </div>
          <div data-tab-panel-2 class="myAppTabPanel am-tab-panel ">
				<div data-am-widget="slider" class="myAppSlider am-slider am-slider-a1" data-am-slider='{"directionNav":false}' >
					<ul class="am-slides">
					<li>
					<img src="img/a4.jpg">

					</li>
					<li>
					<img src="img/a1.jpg">
					</li>
					</ul>
				</div>
					<!-- 新闻列表 -->
		  <div data-am-widget="list_news" class="myAppNews am-list-news am-list-news-default" >



  <div class="am-list-news-bd">
  <ul class="am-list">
    
    
    
    
     <!--缩略图在标题右边-->
      <li class="am-g am-list-item-desced am-list-item-thumbed am-list-item-thumb-right">
        <div class=" am-u-sm-8 am-list-main">
            <h3 class="am-list-item-hd"><a href="###" class="">The Digitally-Fabricated Version of a Map With Red Pins In It</a></h3>


            <div class="am-list-item-text">How awesome would it be to design, while still a student, the product that would set your entire future up? </div>

        </div>
          <div class="am-u-sm-4 am-list-thumb">
            <a href="###" class="">
              <img src="img/b1.jpg"/>
            </a>
          </div>
      </li>
      <li class="am-g am-list-item-desced am-list-item-thumbed am-list-item-thumb-right">
        <div class=" am-u-sm-8 am-list-main">
            <h3 class="am-list-item-hd"><a href="###" class="">Who Knew? The Waterbed Was Invented by an Industrial Design</a></h3>


            <div class="am-list-item-text">You can now order 3D-printed "trophies" documenting your precise travels in 3D space</div>

        </div>
          <div class="am-u-sm-4 am-list-thumb">
            <a href="###" class="">
              <img src="img/b2.jpg" alt="我最喜欢的一张画"/>
            </a>
          </div>
      </li>
      <li class="am-g am-list-item-desced">
        <div class=" am-list-main">
            <h3 class="am-list-item-hd"><a href="###" class="">An Interesting UX Design Departure for Mechanical Wristwatches</a></h3>


            <div class="am-list-item-text">We like seeing designers experimenting with the gradations between two extremes. Take wristwatches, for instance. </div>

        </div>
      </li>
      <li class="am-g am-list-item-desced">
        <div class=" am-list-main">
            <h3 class="am-list-item-hd"><a href="###" class="">An Interesting UX Design Departure for Mechanical Wristwatches</a></h3>
				  <ul data-am-widget="gallery" class="myAppGallery am-gallery am-avg-sm-3
  am-avg-md-3 am-avg-lg-3 am-gallery-default" data-am-gallery="{ pureview: true }" >
      <li>
        <div class="am-gallery-item">
            <a href="###" class="">
              <img src="img/a5.jpg" alt="远方 有一个地方 那里种有我们的梦想"/>
               
            </a>
        </div>
      </li>
      <li>
        <div class="am-gallery-item">
            <a href="###" class="">
              <img  src="img/a4.jpg"  alt="某天 也许会相遇 相遇在这个好地方"/>
               
            </a>
        </div>
      </li>
      <li>
        <div class="am-gallery-item">
            <a href="###" class="">
              <img  src="img/a8.jpg"  alt="不要太担心 只因为我相信"/>
               
            </a>
        </div>
      </li>

  </ul>


            <div class="am-list-item-text">We like seeing designers experimenting with the gradations between two extremes. Take wristwatches, for instance. </div>

        </div>
      </li>
       <li class="am-g am-list-item-desced am-list-item-thumbed am-list-item-thumb-right">
        <div class=" am-u-sm-8 am-list-main">
            <h3 class="am-list-item-hd"><a href="###" class="">Who Knew? The Waterbed Was Invented by an Industrial Design</a></h3>


            <div class="am-list-item-text">You can now order 3D-printed "trophies" documenting your precise travels in 3D space</div>

        </div>
          <div class="am-u-sm-4 am-list-thumb">
            <a href="###" class="">
              <img src="img/b4.jpg" alt="我最喜欢的一张画"/>
            </a>
          </div>
      </li>
        <li class="am-g am-list-item-desced am-list-item-thumbed am-list-item-thumb-right">
        <div class=" am-u-sm-8 am-list-main">
            <h3 class="am-list-item-hd"><a href="###" class="">The Digitally-Fabricated Version of a Map With Red Pins In It</a></h3>


            <div class="am-list-item-text">How awesome would it be to design, while still a student, the product that would set your entire future up? </div>

        </div>
          <div class="am-u-sm-4 am-list-thumb">
            <a href="###" class="">
              <img src="img/b1.jpg"/>
            </a>
          </div>
      </li>
      <li class="am-g am-list-item-desced am-list-item-thumbed am-list-item-thumb-right">
        <div class=" am-u-sm-8 am-list-main">
            <h3 class="am-list-item-hd"><a href="###" class="">Who Knew? The Waterbed Was Invented by an Industrial Design</a></h3>


            <div class="am-list-item-text">You can now order 3D-printed "trophies" documenting your precise travels in 3D space</div>

        </div>
          <div class="am-u-sm-4 am-list-thumb">
            <a href="###" class="">
              <img src="img/b2.jpg" alt="我最喜欢的一张画"/>
            </a>
          </div>
      </li>
    </ul>
  </div>

    </div>

          </div>
      </div>
  </div>




</div>
<!-- 侧边栏内容 -->
<div id="doc-oc-demo2" class="myAppOffcanvas am-offcanvas">
  <div class="am-offcanvas-bar">
    <div class="am-offcanvas-content">
    <div class="myAppOffcanvasUserIco">
    	<img src="img/userico.jpg" alt="" onclick="javascript:location.href='page.jsp'">
    </div>
     <div class="myAppOffcanvasUserName">赵园旭</div>
     <div class="myAppOffcanvasInfo">
     	<li>家庭</li>
     	<li>朋友</li>
     	 <li>同事</li>
     </div>
		<div class="am-container">
  <div class="am-g">
    <div class="am-u-sm-6"><button type="button" class="myAppBtn am-btn am-btn-default am-round am-btn-danger">E-mail</button></div>
    <div class="am-u-sm-6"><button type="button" class="myAppBtn am-btn am-btn-default am-round am-btn-primary">Password</button></div>
  </div>
</div>
     	
    </div>
  </div>
</div>
<!--[if (gte IE 9)|!(IE)]><!-->
<script src="js/jquery.min.js"></script>
<!--<![endif]-->
<!--[if lte IE 8 ]>
<script src="http://libs.baidu.com/jquery/1.11.3/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="assets/js/amazeui.ie8polyfill.min.js"></script>
<![endif]-->
<script src="js/amazeui.min.js"></script>
<script src="js/app.js"></script>
</body>
</html>