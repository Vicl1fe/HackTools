# HackTools
为了提高效率，自己写的一个小工具，来方便日常的工作。

## 功能
### 加解密
- 2023-05-04 新增Commons FileUpload 文件名编码功能，支持多行，例如aa bb cc .jsp。每一行都会进行编码然后拼接。
- 2023-04-28 修复weblogic解密失败问题
- 2023-04-05 在JavaUils增加gzip编解码功能、缩小Class文件功能。
- 2023-04-04 修改JavaUils UI，在主输入框和文件绝对路径处，如果文件绝对路径输入框为空，才会去主输入框取值，编码解码都是一样的
- 2023-03-17 新增jasypt解密，解密功能比较简单。不支持复杂的jasypt加密
- 2023-02-16 新增帆软解密
- 2022-12-23 新增base32编解码、Unicode中文互转
- 新增Base64编码输出到文件、Godzilla解密、AES输出到文件，目前只把保存文件功能添加到了BASE64和AES/CBC，感觉其他的用不着(20220802)
- 目前支持AES、DES、MD5(只有加密)、Weblogic、Druid、XShell
- 加密时默认输出为Base64编码
- 解密时默认输入为Base64编码
- 密钥的编码可选择编码方式(BASE64、TEXT)
- Xshell、Weblogic解密一定选择密钥编码方式为TEXT
- 
### 杀软查询
- 集成了市面上常见的杀软和程序

### 文本处理
- 目前只支持去重

### JavaUtils
- 2022-12-23 可添加垃圾字符到恶意的序列化文件
- 2022-12-23 bcel编解码

### Bug
- 给文本域加上滚动条后，UI界面会时不时的变化，所以暂时没有添加滚动条功能。
- weblogic解密有问题，具体原因：https://www.cnblogs.com/zhangyy3/p/13672261.html 。暂时没找到好的解决方法

## 截图
[![jMzGtS.png](https://s1.ax1x.com/2022/07/01/jMzGtS.png)](https://imgtu.com/i/jMzGtS)
[![jMzJfg.png](https://s1.ax1x.com/2022/07/01/jMzJfg.png)](https://imgtu.com/i/jMzJfg)
[![jMz8k8.png](https://s1.ax1x.com/2022/07/01/jMz8k8.png)](https://imgtu.com/i/jMz8k8)



# 致谢
- https://github.com/TideSec/Decrypt_Weblogic_Password
- https://github.com/JDArmy/SharpXDecrypt