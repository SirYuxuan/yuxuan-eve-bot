### 雨轩EVE机器人
****
**如果此项目对你有帮助，麻烦点个Star**

`此版本仅包含基础功能，完善的插件机制，实现MessagePlugin接口即可自动加载插件，插件目录为plugins，插件开发请参考plugins目录下的示例插件。`
### 项目介绍
*目前实现的功能如下*
1. 价格查询
2. 价格查询的别名设置(管理员设置全局，群员设置的自己生效)
3. PAP查询
4. LP查询
### 使用说明
想要启动此项目需要导入sql下的三张表，然后修改config/application-db.yml文件中的数据库连接
- table1: eve_type(eve所有物品的中英文对照名字)
- table2: eve_type_alias(用户自定义的别名)
- table3: sys_config(系统配置表)

`ps: sys_config 中配置与联盟Seat通讯的Cookie可通过浏览器抓包获得,有此项配置可实现PAP查询`

其余表结构不影响项目运行，LP查询数据需要对接军团系统数据库所以暂不提供
