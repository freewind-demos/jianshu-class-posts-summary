package in.freewind.jianshu

import net.ruippeixotog.scalascraper.scraper.ContentExtractors.{element => _, elementList => _}

object DaJiangTangApp extends App {

  val users = List(
    User("谭艺冰", "a6b3075161bd", "李彤欣", Nil),
    User("吴立宁", "45e7b3524cf0", "曹宇鹏", Nil),
    User("李天", "2823d35ef6d5", "王运", Nil),
    User("郭茹", "54aa25d86396", "朱榕", Nil),
    User("安洋", "b2d6a8f9b5a7", "曹宇鹏", Nil),
    User("谭雅翔", "72970fc87336", "孙进", Nil),
    User("王丹娜", "4c0a06c8a1f9", "朱榕", Nil),
    User("游茹玉", "f9058daafe41", "孙进", Nil),
    User("张培", "46a98ec591f2", "李燕子", Nil),
    User("王张军", "71022f2e6c77", "周婧", Nil),
    User("王渊博", "52927c3889fd", "赵文博", Nil),
    User("付红", "aa813305d667", "张羽辰", Nil),
    User("赵琪琪", "2db65e841261", "李燕子", Nil),
    User("马红", "9a316c32b7f1", "陈洋", Nil),
    User("骆承秀", "f5de73654f02/", "周婧", Nil),
    User("刘涛", "276446dd8c0a", "陈洋", Nil),
    User("祁燕子", "57e9a7f46b24", "周婧", Nil),
    User("宁润婷", "ad065eec8811", "阎昱", Nil),
    User("程雨柔", "38cc9391d17d", "陈洋", Nil),
    User("冯雯", "6f0e582882ba", "孙进", Nil),
    User("邱敏敏", "b49e2048bab0", "薛倩", Nil),
    User("李爽祺", "ea4648bc3d08", "玛莉", Nil),
    User("秦臻", "a5757b7c55fe", "赵文博", Nil),
    User("汪之涛", "c5ccc9f40501", "玛莉", Nil),
    User("张从飞", "9ce3426fdb1c", "袁慎建", Nil),
    User("李玥", "389f478c92c8", "玛莉", Nil),
    User("张颖嘉", "b2cf132f508d", "张志慧", Nil),
    User("王柏元", "ef49e6b7ec1e", "张晓飞", Nil),
    User("李英健", "3c682ea44ee2", "屈鉴铭", Nil),
    User("王亚鑫", "048f932015bc", "屈鉴铭", Nil),
    User("党森", "edc9e5b7ce20", "李彤欣", Nil),
    User("邵娜娜", "17f27b3e3233", "王运", Nil),
    User("王普", "b26a523e3d60", "张羽辰", Nil),
    User("陈娟娟", "0bd3779cf676", "薛倩", Nil),
    User("贾祎恺", "ff02b6e2b83f", "朱榕", Nil),
    User("何疆乐", "ab3e5acc3f2c", "严嘉阳", Nil),
    User("刘俊男", "585fc558d5bc", "李燕子", Nil),
    User("燕春翔", "6fc9411d1f1f", "张志慧", Nil),
    User("黄炎", "4ce7460cc2f4", "申彤", Nil),
    User("张哲雯", "8f4a554c29e8", "张翔", Nil),
    User("刘本庆", "5a2675fba15e", "曹宇鹏", Nil),
    User("张琮", "28f9a2e97e4d", "薛倩", Nil),
    User("李优优", "ca35fbe16b4f", "申彤", Nil),
    User("耿团团", "d4b0a0f96fe8", "张翔", Nil),
    User("杨琛璐", "4f36a8b0a33a", "张羽辰", Nil),
    User("王平升", "610f62fc5cf1", "屈鉴铭", Nil),
    User("薛妮", "061cb0850ee2", "申彤", Nil),
    User("梁法成", "7d8bb76cfe4f", "阎昱", Nil),
    User("谷楠楠", "f111ef01770a", "阎昱", Nil),
    User("汪之涛", "c5ccc9f40501", "玛莉", Nil),
    User("张苗", "b14cbd37028f", "张晓飞", Nil)
  ).map(user => {
    user.copy(articles = ArticlePages.getAllArticles(user.jianshuId))
  })

  Report.render(users.sortBy(u => -u.articles.size), "小buddy")

}



