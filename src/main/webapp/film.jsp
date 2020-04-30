<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
  <head>
    
    <title>电影汇集</title>
    <script src="static/js/jquery-2.1.1.min.js"></script>
	<style>
	.list{
  position: relative;
background-color: rgb(248, 248, 248);
width: 100%;height: 100%;
font-family: 'Segoe UI', 'Roboto', Tahoma;
  z-index: 10;
}
#s_seach{
  position: absolute;
  width:100%;
  height: 100%;
  background-color:#999;
  z-index: 1;
  opacity:0.6;
}
#s_seach input{
border: solid 2px #795548;
background-color: #fff;
border-left: 0px;
}
#s_seach .img{
border: solid 2px #795548;
border-right: 0px;
}

.side input { 
   border: solid 2px #777;
  margin: 5px 10px; 
   border-left: 0px;
   height:40px;
  margin-left:0px;
   width: calc(100% - 55px);
  outline: none;
}
.side .img{
  margin: 5px 10px; 
  margin-right: 0px;
  border: solid 2px #777;
border-right: 0px;width:60px;
height:40px;
background-color:#fff;
}
.loaded {
  /* position: fixed; */
  background-color: rgb(248, 248, 248);
  width: 100%;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 10;
}

.loaded div{
  margin-right: 15px;
  list-style: none;
  float: left;
   transition: all .2s ease-in;
  color: #999;
  font-weight:400px;
  cursor: pointer;
 
}

.loaded .li,
.loaded  .selected { 
  color: #795548;
  font-weight: 600; 
}



.content{
  /* background-color:red;  */
}
.content .film-list{
   position: relative;
    list-style: none;
  margin: 0 20px;
   padding-left: 10px;
}

.content .list-li {
 
   
  margin-top:3px;
  opacity: 1;
  border: solid 1px rgba(0, 0, 0, 0.2);
  border-radius: 2px;
  box-shadow: 0px 2px 10px 0px rgba(0, 0, 0, 0.25);
  width: 140px;
  /* transform: translateY(120rpx); */
  position: relative;
  padding: 5px;
  display: inline-block;
  margin-right: 10px;
  margin-bottom: 20px;
  vertical-align: top;
  transition: all .5s ease;
}

.content .list-li image { 
  height: 200px; 
  width: 140px;
  background-color: #607D8B; 
  background-size: cover; 
  transition: all .8s ease; 
}
.content .span { 
  font-size: 14px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis; 
}
.content .small { 
  color: #666; 
  font-size: 11px; 
}
.layout_horizontal{
white-space:nowrap;
overflow:hidden;float:left;
}
	
	</style>
  </head>
  
  <body>
     <div class="list" >
  <div class="side  layout_horizontal" id="{{css}}">
  <div class="img"><img src='img/iconfont-seacher.png' style="height:100%;width:40px;margin:1px 7px;"></img></div>
  <input type="text" class="search" name="id" placeholder='输入关键词' bindtap='bindfocus' bindfocus="bindfocus" bindblur="bindblur"/>
  </div>
  <div class="loaded">
    <div hover-class="li" wx:if="{{css==''}}" class='{{currentData == "all"?"selected" : ""}}' data-current="all" bindtap='checkCurrent'>所有</div>
    <div hover-class="li"  wx:if="{{css==''}}"class='{{currentData == "0"?"selected" : ""}}' data-current="0" bindtap='checkCurrent'>电影</div>
    <div hover-class="li"  wx:if="{{css==''}}"class='{{currentData == "1"?"selected" : ""}}' data-current="1" bindtap='checkCurrent'>电视剧</div>
  </div>
  <div class="content">
    <div class="film-list">
      <div wx:for="{{list}}" wx:key="{{item}}" class="list-li" data-index="{{item.id}}" bindtap='link'>
        <image src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mP8zw8AAhMBENYXhyAAAAAASUVORK5CYII=" 
        style="background-image: url({{item.imgurl}})"></image>
        <div class="span">{{item.imgname}}</div>
       <div class="small">{{item.credate}}</div>
      </div>

    </div>

  </div>
</div>
  </body>
</html>
