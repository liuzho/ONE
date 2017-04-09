
# 说高仿，其实做成了个山寨...

- **目前完成度看图：**

![home.gif](https://github.com/liuzho/ONE/raw/master/gif/home.gif)![read.gif](https://github.com/liuzho/ONE/raw/master/gif/read.gif)![music.gif](https://github.com/liuzho/ONE/raw/master/gif/music.gif)![movie.gif](https://github.com/liuzho/ONE/raw/master/gif/movie.gif)

## 关于我
 1. 最近火力全开正在实现中，感兴趣的朋友可以**Start**一下，关注我的后续动作
 2. 虽是火力全开，但学生党目前还有课，有进度但效率可能不太高
 3. 联系我：qq354295878@gmail.com(一个邮箱暴露两个联系方式，完美)
 4. 另：大三狗一枚，求实习岗位中


## 开发相关
 1. 更新：
```
	-2017-4-9 ：
		1. 删除电影简介activity，替换成popupWindow展示
		2. 每日一图的popupWindow长按图片实现通过Picasso保存图片到制定路径

	-2017-4-8 ：
		1. 自定义RecyclerView实现滚动实时监听，彻底解决RecyclerView滚动时toolbar显示bug
		2. 添加水波纹点击效果
		3. 添加分享按钮弹出popupWindow，包含弹出动画/退出动画
		4. OneList的每日一图点击弹出popupWindow，长按pop图片，弹出Snackbar提示是否保存图片

	-2017-4-7 ：
		1. 更换部分图片
		2. 添加各详情页的评论列表
		3. 优化部分代码
		注：后期更新频率会减少

	-2017-4-6 ：
		1. 添加BaseFragment、优化fragment代码/逻辑
		2. 自定义RecyclerView实现滚动实时监听(未完成)
		3. 添加作者信息Activity(未完成/无法完成：无作者文章API)

	-2017-4-5 ：
		1. 解决tab切换时toolbar显示异常的bug
		2. 优化fragment代码/逻辑

	-2017-4-2 ：
		1. 添加页面加载动画（帧动画）

	-2017-3-30：
		1. 优化代码结构：baseActivity&adapter&布局

	-2017-3-27：
		1. 实现底部tab点击，主界面所有tab均有内容展现

	-2017-3-25：
		1. 增加音乐详情页（所有详情页均无显示评论列表，后续加）

	-2017-3-24：
		1. 增加电影详情页以及部分细节调整/实现

	-...再早没写。一次性出来的...
```

 2. ONE的API：[ONE-API](https://github.com/jokermonn/-Api/blob/master/ONEv3.5.0~.md)
	API来自网络，若侵犯《ONE一个》权利，本人将及时停止使用并删除相关内容
	**PS**：感谢分享的哥们儿；也提示各位朋友不要用于商业用途
	**废话**：《ONE》这个APP的API和JSON不忍直视，原APP卡顿明显，体验差强人意。

 3. 使用到的库：
>- compile 'com.squareup.retrofit2:retrofit:2.2.0'//Retrofit
>- compile 'com.squareup.retrofit2:converter-gson:2.0.2'//Gson
>- compile 'com.squareup.picasso:picasso:2.5.2'//picasso
>- compile 'com.android.support:design:25.3.1'//MD
>- ompile 'com.android.support:recyclerview-v7:25.3.1'//recyclerView