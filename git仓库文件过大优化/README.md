[参考](https://blog.csdn.net/Y0W1as5eg37urFdS/article/details/123539994)

# 下载封装好的jar包（把你下载的bfg的jar包复制到这个项目同目录下）
$ wget https://repo1.maven.org/maven2/com/madgag/bfg/1.13.0/bfg-1.13.0.jar

[bfg-1.13.0.jar](bfg-1.13.0.jar)


# 克隆的时候需要--mirror参数
git clone --mirror git@github.com:MineApplication/BsDiffApplication.git

# 运行BFG来清理存储库
$ java -jar bfg-1.13.0.jar --strip-blobs-bigger-than 100M BsDiffApplication.git

# 去除脏数据
$ cd BsDiffApplication.git
$ git reflog expire --expire=now --all
$ git gc --prune=now --aggressive

# 推送上去
# 此推将更新远程服务器上的所有refs分支
$ git push


# 删除所有的名为'id_dsa'或'id_rsa'的文件
$ java -jar bfg.jar --delete-files id_{dsa,rsa}  my-repo.git

# 删除所有大于50M的文件
$ java -jar bfg.jar --strip-blobs-bigger-than 50M  my-repo.git

# 删除文件夹下所有的文件
$ java -jar bfg-1.13.0.jar --delete-folders build  BsDiffApplication.git
