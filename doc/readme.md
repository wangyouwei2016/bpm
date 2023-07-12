


- 第一次建议执行 full SQL 如 `sql/mysql/full/agilebpm_full.sql`

- upgrade 为每个版本升级的SQL，请根据自己当下版本，执行至 升级后的版本

- changeSQL 是每个版本内 更新的SQL，用于开发环境，最终会整理成 upgradeSQL 。 




我们 s2 是最新的初始化SQL

如：我新增了个内置字典，就加个create语句到changeSQL，修改了就加个update。
如有批量数据修复的，就写相关修复SQL进去。

有的人本地开发，你加了个字段，他那里就要报错。所以要有 changeSQL 他们发现报错了，就会到changeSQL找


 