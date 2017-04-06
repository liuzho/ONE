# 高仿《ONE》

- 目前完成度：

	![](https://github.com/liuzho/ONE/blob/master/demo.gif)

- 废话：《ONE》这个APP明显是个外包公司做的，而且极其不负责任...
		API和JSON不忍直视\还有明显bug....
		应用滑动卡顿明显...
		交互体验垃圾

- 更新：

	2017-4-6 ：添加BaseFragment、优化fragment代码/逻辑、自定义RecycleView实现滚动实时监听(未完成)
			添加作者信息Activity(未完成)
	
	2017-4-5 ：解决tab切换时toolbar显示异常的bug；优化fragment代码/逻辑

	2017-4-2 ：添加页面加载动画（帧动画）

	2017-3-30：优化代码结构 - baseActivity&adapter&布局

	2017-3-27：实现底部tab点击，主界面所有tab均有内容展现

	2017-3-25：增加音乐详情页（所有详情页均无显示评论列表，后续加）

	2017-3-24：增加电影详情页以及部分细节调整/实现

...再早没写，忘了

- 使用到的库：
 1. compile 'io.reactivex.rxjava2:rxandroid:2.0.1'//RxAndroid
 2. compile 'io.reactivex.rxjava2:rxjava:2.0.7'//RxJava
 3. compile 'com.squareup.retrofit2:retrofit:2.2.0'//Retrofit
 4. compile 'com.squareup.retrofit2:converter-gson:2.0.2'//Gson
 5. compile 'com.squareup.picasso:picasso:2.5.2'//Picasso