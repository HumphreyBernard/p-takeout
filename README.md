# p-takeout

# 外卖平台

## 目录

*   [1. 项目简介](#1-项目简介)

*   [2. 技术选型](#2-技术选型)

    *   [Ⅰ. 前端](#ⅰ-前端)

    *   [Ⅱ. 后端](#ⅱ-后端)

    *   [Ⅲ. 部署](#ⅲ-部署)

*   [3. 项目大纲](#3-项目大纲)

    *   [第一天：](#第一天)

        *   [（1） 环境搭建](#1-环境搭建)

        *   [（2）实体类的创建](#2实体类的创建)

        *   [（3）项目结构的建立](#3项目结构的建立)

        *   [（4）页面与Controller的交互](#4页面与controller的交互)

        *   [（5）通用泛型类R<>的设计](#5通用泛型类r的设计)

    *   [第二天](#第二天)

    *   [员工管理](#员工管理)

        *   [（1）登录、退出功能的实现](#1登录退出功能的实现)

            *   [（a）登录](#a登录)

            *   [（b）退出](#b退出)

        *   [（2）添加员工](#2添加员工)

        *   [（3）员工信息的分页查询（支持按照姓名模糊查询）](#3员工信息的分页查询支持按照姓名模糊查询)

            *   [（a）配置分页拦截器](#a配置分页拦截器)

            *   [（b）分页查询](#b分页查询)

        *   [（4）对员工的信息修改](#4对员工的信息修改)

            *   [（a）获取员工信息作为回显](#a获取员工信息作为回显)

            *   [（b）对指定的员工进行信息的修改](#b对指定的员工进行信息的修改)

    *   [第三天](#第三天)

    *   [菜品分类管理](#菜品分类管理)

        *   [（1）新增一个分类](#1新增一个分类)

        *   [（2）实现分页](#2实现分页)

        *   [（3）删除分类](#3删除分类)

        *   [（4）修改分类](#4修改分类)

            *   [（a）获取](#a获取)

            *   [（b）修改](#b修改)

    *   [第四天](#第四天)

    *   [文件上传与下载](#文件上传与下载)

        *   [（1）在.yml配置文件中设置上传文件的基本路径](#1在yml配置文件中设置上传文件的基本路径)

        *   [（2）创建FileController完成文件的上传与下载](#2创建filecontroller完成文件的上传与下载)

            *   [（a）路径的统一](#a路径的统一)

            *   [（b）获取上传文件基本路径](#b获取上传文件基本路径)

            *   [（c）文件上传：浏览器端→服务器端](#c文件上传浏览器端服务器端)

            *   [（d）文件下载：服务器端→浏览器端](#d文件下载服务器端浏览器端)

    *   [Dto类的设计](#dto类的设计)

    *   [菜品管理](#菜品管理)

        *   [（1）添加菜品](#1添加菜品)

            *   [（a）分析](#a分析)

            *   [（b）在分类controller中获取所有分类集合](#b在分类controller中获取所有分类集合)

            *   [（c）菜品的提交](#c菜品的提交)

        *   [（2）分页+模糊查询](#2分页模糊查询)

        *   [（3）修改菜品](#3修改菜品)

            *   [（a）前端](#a前端)

            *   [（b）后端](#b后端)

        *   [（4）删除、批量删除菜品](#4删除批量删除菜品)

        *   [（5）停售、批量停售菜品](#5停售批量停售菜品)

            *   [（a）前端](#a前端-1)

            *   [（b）后端](#b后端-1)

    *   [第五天](#第五天)

    *   [套餐管理](#套餐管理)

        *   [（1）什么是套餐](#1什么是套餐)

        *   [（2）Dto类的设定](#2dto类的设定)

        *   [（3）新建套餐](#3新建套餐)

            *   [（a）获取套餐分类](#a获取套餐分类)

            *   [（b）向套餐中添加菜品](#b向套餐中添加菜品)

            *   [（c）保存新建的套餐](#c保存新建的套餐)

            *   [（d）controller中的操作](#dcontroller中的操作)

        *   [（4）模糊+分页查询](#4模糊分页查询)

        *   [（5）停售、批量停售套餐](#5停售批量停售套餐)

        *   [（6）删除、批量删除套餐](#6删除批量删除套餐)

            *   [（a）前端页面分析](#a前端页面分析)

            *   [（b）后端分析](#b后端分析)

        *   [（7）修改套餐](#7修改套餐)

    *   [第六天](#第六天)

    *   [前台用户登录功能](#前台用户登录功能)

        *   [（0）修改登录过滤器](#0修改登录过滤器)

        *   [（1）用户的实体类](#1用户的实体类)

        *   [（2）登录](#2登录)

            *   [（a）前端](#a前端-2)

            *   [（b）后端](#b后端-2)

    *   [前台用户地址相关](#前台用户地址相关)

        *   [（1）地址的实体类](#1地址的实体类)

        *   [（2）添加地址](#2添加地址)

            *   [（a）前端](#a前端-3)

            *   [（b）后端](#b后端-3)

        *   [（3）查看当前用户所有地址](#3查看当前用户所有地址)

            *   [（a）前端](#a前端-4)

            *   [（b）后端](#b后端-4)

        *   [（4）添加默认地址](#4添加默认地址)

            *   [（a）前端](#a前端-5)

            *   [（b）后端](#b后端-5)

        *   [（5[Git.zip](https://github.com/HumphreyBernard/p-takeout/files/8708109/Git.zip)
）更新地址](#5更新地址)

            *   [（a）前端](#a前端-6)

            *   [（b）后端](#b后端-6)

        *   [（6）删除地址](#6删除地址)

            *   [（a）前端](#a前端-7)

            *   [（b）后端](#b后端-7)

        *   [（7）查询默认地址](#7查询默认地址)

            *   [（a）前端部分见“第七天-下单-前端”](#a前端部分见第七天-下单-前端)

            *   [（b）后端](#b后端-8)

    *   [前台用户购物车相关](#前台用户购物车相关)

        *   [（1）购物车实体类](#1购物车实体类)

        *   [（2）添加一个到购物车](#2添加一个到购物车)

            *   [（a）前端](#a前端-8)

            *   [（b）后端](#b后端-9)

        *   [（3）减少一个数量](#3减少一个数量)

            *   [（a）前端](#a前端-9)

            *   [（b）后端](#b后端-10)

        *   [（4）查询当前用户的购物车信息](#4查询当前用户的购物车信息)

            *   [（a）前端](#a前端-10)

            *   [（b）后端](#b后端-11)

        *   [（5）清空购物车](#5清空购物车)

            *   [（a）前端](#a前端-11)

            *   [（b）后端](#b后端-12)

    *   [第七天](#第七天)

    *   [前台用户订单相关](#前台用户订单相关)

        *   [（1）实体类](#1实体类)

        *   [（2）下单](#2下单)

            *   [（a）前端](#a前端-12)

            *   [（b）后端](#b后端-13)

# 1. 项目简介

项目源自：

[黑马程序员Java项目实战《瑞吉外卖》，轻松掌握springboot + mybatis plus开发核心技术的真java实战项目\_哔哩哔哩\_bilibili](https://www.bilibili.com/video/BV13a411q753?spm_id_from=333.1007.top_right_bar_window_custom_collection.content.click "黑马程序员Java项目实战《瑞吉外卖》，轻松掌握springboot + mybatis plus开发核心技术的真java实战项目_哔哩哔哩_bilibili")

# 2. 技术选型

## Ⅰ. 前端

1.  html、js、css

2.  网页设计：element-ui

3.  框架：vue、axios

## Ⅱ. 后端

1.  Java、MySQL

2.  数据库交互框架：MybatisPlus

3.  mvc框架：SpringBoot

## Ⅲ. 部署

# 3. 项目大纲

## 第一天：

### （1） 环境搭建

*   通过start.spring.io创建SpringBoot工程，勾选lombok、MySQL、SpringWeb。

*   导入更多的依赖

[额外的依赖](额外的依赖/额外的依赖.md "额外的依赖")

*   将application.properties转换为application.yml。在application.yml中配置端口号、数据源、mybatisplus配置

[yml的初步配置](yml的初步配置/yml的初步配置.md "yml的初步配置")

*   向resources目录下导入页面数据

*   创建数据库、数据表

[数据表的图结构](数据表的图结构/数据表的图结构.md "数据表的图结构")

### （2）实体类的创建

根据表中字段创建类属性，使用lombok注释@Data、@NoArgsConstructor、@AllArgsConstructor来获得getter、setter、toString、equals、hashcode、构造函数

### （3）项目结构的建立

![](image/image_5UftZ3EQHo.png)

### （4）页面与Controller的交互

Controller返回一个通用类，通过注解@RestController标记controller层，方便页面统一调用相关信息。

### （5）通用泛型类R<>的设计

1.  指定成功/失败的属性**code（Integer）**：1为成功，0为失败

2.  成功：通过属性**data（T）** 来将具体数据转化为json对象使页面调用

3.  失败：通过属性**msg（String）** 来向用户提示错误

4.  通过方法指定成功与失败

    1.  成功：使用静态方法\*<T> R<T> success(T object)\* ，指定code为1，填充data

    2.  失败：使用静态方法\*<T> R<T> error(String msg)\* ，指定code为0，填充msg

5.  属性map（Map）存放动态数据

6.  方法R\<T>add（String key，Object value）向map中添加一条数据

[R](R/R.md "R")

***

## 第二天

## 员工管理

EmployeeController、EmployeeService、EmployeeMapper、Employee

[entity——Employee](entity——Employee/entity——Employee.md "entity——Employee")

在EmployeeController上添加注解@RestController、@ResultMapping("/employee")

### （1）登录、退出功能的实现

#### （a）登录

1.  参数处理

    1.  用户在登录界面输入账号、密码（**在方法参数中要封装为Employee对象。由于前端发送的是json对象，因此需要添加注解@RequestBody自动将json转换为java对象）**，点击登录发送请求

    2.  请求分析

        1.  get请求：...？username=...\&password=...——不安全，容易泄露信息

        2.  因此使用post请求：在方法上添加@PostMapping("/login")

    3.  对密码进行md5加密（数据库中不保存原有密码）：DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF\_8))

    4.  使用条件查询LambdaQueryMapper\<Employee>向数据表中根据用户名查询对应用户信息

        1.  查到

            1.  密码错误：返回错误信息R.error(相应信息)

            2.  被系统禁用：返回错误信息R.error(相应信息)

            3.  正常状态：

                [登录功能的前端代码](登录功能的前端代码/登录功能的前端代码.md "登录功能的前端代码")

                通过前端代码（res.data）可以看出需要返回R\<Employee>

        2.  没查到

            返回错误信息R.error(相应信息)

2.  通过**request**（HttpServletRequest）实现登录用户信息的保存

    向request保存用户的id，命名为employee

3.  **后端返回的是R对象，前端要使用json对象。处理方法为：**

    1.  创建配置类WebMvcConfig，使用@Configuration对类进行标注

    2.  覆盖重写方法extendMessageConverters以扩展mvc框架的消息转换器

    3.  创建java←→json对象映射器对象：JacksonObjectMapper

    4.  注：创建了配置类之后，默认的静态资源包static失效，因此将静态资源移出并覆盖重写方法addResourceHandlers来映射静态资源

    [配置类：WebMvcConfig ](配置类：WebMvcConfig/配置类：WebMvcConfig.md "配置类：WebMvcConfig ")

    [映射类：JacksonObjectMapper ](映射类：JacksonObjectMapper/映射类：JacksonObjectMapper.md "映射类：JacksonObjectMapper ")

#### （b）退出

1.  前端：清除localStorage信息

    [前端代码](前端代码/前端代码.md "前端代码")

    通过前端代码发现只用到了res.code，因此仅仅返回R\<String>即可

2.  后端：清除session信息

### （2）添加员工

在员工管理页面member/list.html上点击“添加员工”按钮，跳转到添加员工member/add.html的界面。在该界面上输入了相应信息后点击“保存”按钮，将数据保存到数据库并重定向至员工管理界面member/list.html，如果点击“保存并继续添加”按钮，则可以继续在member/add.html济宁添加用户操作。

1.  前端：处理响应事件，将用户名、姓名、手机号、性别、身份证号封装为json对象传给后端

    [前端代码](前端代码_1/前端代码.md "前端代码")

    根据前端代码，可以确定后端方法返回值为R\<String>，方法参数为\*\*@RequestBody Employee\*\*

2.  后端

    1.  将Employee对象的其他属性填充完全

        1.  \~\~员工编号：添加到数据库时通过雪花算法自动生成\~\~

        2.  密码：自动填充123456的md5加密格式

        3.  创建、修改时间：自动填充now

        4.  创建、修改用户：自动填充当前登录用户的id

        5.  \~\~员工状态：默认值为1\~\~

    2.  创建修改时间、创建修改用户的自动填充\*\*（自动填充是指不调用方法，不使用属性，自动的填充相关字段）\*\*

        由于在新增、修改信息、修改状态等操作下都需要针对这几个字段进行操作，因此

        1.  在entity相关属性之上添加注解@TableField(fill = FieldFill.INSERT\_UPDATE)（添加、修改时自动填充）或@TableField(fill = FieldFill.INSERT)（添加时自动填充）

        2.  编写自动填充的类

            1.  时间的填充：LocalDateTime.now()

            2.  用户的填充：使用本地线程LocalThread存储id——将其封装为一个类，统一的获取和设置本地线程

            3.  什么时候向本地线程设置？登录后进行设置（通过过滤器后再设置）

            4.  编写登录验证过滤器（不能不经过登录直接访问后台管理页面），在通过过滤条件后设置当前用户的id

            [本地线程工具类BaseContext](本地线程工具类BaseContext/本地线程工具类BaseContext.md "本地线程工具类BaseContext")

            [自动填充数据处理器MetaObjectHandler](自动填充数据处理器MetaObjectHandler/自动填充数据处理器MetaObjectHandler.md "自动填充数据处理器MetaObjectHandler")

            [登录验证过滤器LoginCheckFilter](登录验证过滤器LoginCheckFilter/登录验证过滤器LoginCheckFilter.md "登录验证过滤器LoginCheckFilter")

    3.  调用service的save方法向数据库保存数据并返回R.success("用户添加成功")

### （3）员工信息的分页查询（支持按照姓名模糊查询）

#### （a）配置分页拦截器

[分页拦截器](分页拦截器/分页拦截器.md "分页拦截器")

#### （b）分页查询

1.  参数：

    1.  当前页数

    2.  一页中数据数

    3.  （可选）模糊查询：将姓名封装为Employee对象

2.  流程

    1.  创建分页器IPage\<Employee>

    2.  创建条件查询构造器

    3.  调用service方法得到并填充到1中的分页器对象，返回R.success(pageInfo)

### （4）对员工的信息修改

[前端](前端/前端.md "前端")

#### （a）获取员工信息作为回显

1.  根据id查询用户

    1.  有，返回R.success(byId)

    2.  没有，返回 R.error("没有查询到对应员工信息")

#### （b）对指定的员工进行信息的修改

1.  \~\~js只能处理16位数字，但long有19位，会造成精度丢失 → 类JacksonObjectMapper进行转换并序列化为json对象即可（见上）\~\~

2.  \~\~公共字段自动填充（见上）\~\~

3.  根据id更新，返回R.success("员工信息修改成功")

[EmployeeController](EmployeeController/EmployeeController.md "EmployeeController")

***

## 第三天

## 菜品分类管理

[entity——Category](entity——Category/entity——Category.md "entity——Category")

### （1）新增一个分类

[前端代码](前端代码_1_2/前端代码.md "前端代码")

通过分析前端代码，可以得知后端方法

1.  返回值为R\<String>即可

2.  参数需要用@RequestBody标记Category对象

3.  后端分析

    1.  category对象中实际有属性：

        1.  name、type、sort

        2.  id在添加到数据库后自动填充

        3.  这里的删除使用逻辑删除@TableLogic，数据库默认为0

        4.  其余属性设置为自动填充

    2.  “新增分类”的分析

        1.  逻辑删除：对一条数据的删除实际上是对字段is\_deleted的值进行修改：0→1

        2.  对表的增删改查：增删改查的sql语句会在最后附加条件：where is\_deleted = 0

        3.  新建分类时会遇到一个问题：新建的菜品名称是否在已经被逻辑删除的数据里面

        4.  如果在，则应将is\_deleted的值进行修改：1→0（update）

        5.  如果不在，则应添加一条字段（save）

    3.  因此，mybatisplus自带的save方法已经不能满足需求，在service中添加一方法saveOrUpdate（Category category），按照上面的逻辑进行操作

        1.  判断是否为逻辑删除的数据

            1.  在CategoryMapper中声明抽象方法：Category selectInDeleted(@Param("cat") String cName);

            2.  在CategoryMapper.xml中定义sql语句

            ```xml
            <select id="selectInDeleted" resultType="category">
                select *
                from category
                where name = #{cat}
                  and is_deleted = 1
            </select>
            ```

        2.  是，更新

            1.  1中的查询结果不为null

            2.  继续在CategoryMapper中定义方法：void updateLogicDeleted(@Param("identifier") Long id);，并在xml中定义sql

            ```xml
            <update id="updateLogicDeleted">
                update category
                set is_deleted = 0
                where id = #{identifier}
            </update>
            ```

        3.  不是，添加

            使用mapper自带的insert方法添加

    4.  controller调用3中方法，返回R.success("新增分类成功")

### （2）实现分页

1.  参数：页数page、一页的数据量pageSize

2.  返回：疯传整改R里面的分页对象

3.  流程：

    1.  根据页码与数据量构造分页对象

    2.  调用service自带的page方法更新1中的对象

    3.  返回

### （3）删除分类

1.  目的：删除一个分类

2.  注意：分类下有菜品或者套餐，提示用户不能删除

3.  因此不能直接调用service的removeById方法，应该在业务层新建一个先判断后删除的方法，声明为void removeAfterJudge(Long id)

    1.  是否关联菜品、是否关联套餐

        *   判断关联：根据分类的id向dish表中查询有相应字段的记录数，大于0即为有关联

        *   有关联就抛出异常，由全局异常处理器处理异常

    2.  根据id删除

### （4）修改分类

[前端代码](前端代码_1_2_3/前端代码.md "前端代码")

#### （a）获取

在前端获取

#### （b）修改

1.  put方式传参为json对象，需要在参数潜艇加@RequestBody注解

2.  根据id更新

3.  返回R.success("修改成功")

[CategoryController](CategoryController/CategoryController.md "CategoryController")

***

## 第四天

## 文件上传与下载

在“菜品”与“套餐”管理中，展示“菜品”与“套餐”列表的时候需要同时向用户展示相应图片；添加“菜品”与“套餐”的时候需要上传图片然后显示在对应位置上，因此需要创建文件上传与下载的控制器

### （1）在.yml配置文件中设置上传文件的基本路径

```yaml
up-and-down-file:
  path: 图片资源所在的文件夹的绝对路径\
```

### （2）创建FileController完成文件的上传与下载

#### （a）路径的统一

无论在哪里，文件上传的url都为“/common/upload”，文件下载的url都为“/common/download”。因此将controller类标注为@RequestMapping("/common")，对应方法再标注为"/upload"或"/download"

#### （b）获取上传文件基本路径

从.yml配置文件中获取设置的基本路径，方法为设置注解@Value("\${up-and-down-file.path}")

#### （c）文件上传：浏览器端→服务器端

1.  上传的文件以MultipartFile的类型传入

2.  流程

    1.  根据MultipartFile对象得到文件名或创建新的文件名

    2.  判断根目录的存在性，没有就创建新的

    3.  MultipartFile对象是一个临时文件，必须要经过转存才能保存——调用transferTo方法

    4.  成功返回R.success(fName)，失败则抛出异常

#### （d）文件下载：服务器端→浏览器端

1.  图片上传的前端代码中

    1.  “action="/common/upload"”：调用控制器中的upload方法，得到json对象（data为文件名）

    2.  “:on-success="handleAvatarSuccess"”：调用js方法handleAvatarSuccess，调用download方法（使用了1中的json对象）

    ```javascript
    handleAvatarSuccess(response, file, fileList) {
        // 拼接down接口预览
        if (response.code === 0 && response.msg === '未登录') {
            window.top.location.href = '../login/login.html'
        } else {
            this.imageUrl = '/common/download?name=${response.data}'
            this.ruleForm.image = response.data
        }
    }
    ```

<!---->

1.  后端：

    1.  读文件

    2.  设置返回文件类型

    3.  写回浏览器（通过response获取输出流）

[FileController](FileController/FileController.md "FileController")

## Dto类的设计

在添加菜品的时候，除了菜品对应的Dish类中的属性外，还有DishFlavor类中的口味名称和对应的数据这两个属性。在添加菜品的时候，点击提交按钮，所填的数据以json对象的形式发送至服务器端，controller以对应的java对象接收，因此我们需要将Dish类延申，让他包括口味等数据。

做法是创建DishDto类，继承Dish

[DishDto](DishDto/DishDto.md "DishDto")

## 菜品管理

### （1）添加菜品

#### （a）分析

![](image/image_OdtaHuNT9M.png)

#### （b）在分类controller中获取所有分类集合

```java
@GetMapping("/list")
public R<List<Category>> getList(Category category) {
    // 条件
    LambdaQueryWrapper<Category> l = new LambdaQueryWrapper<>();
    l.eq(!Objects.equals(null, category), Category::getType, category.getType());
    l.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

    // 查询
    List<Category> list = categoryService.list(l);

    return R.success(list);
}
```

#### （c）菜品的提交

1.  前端

    [前端分析](前端分析/前端分析.md "前端分析")

2.  后端

    1.  由前端分析而得，需要返回R.success("新增菜品成功")

    2.  方法参数为用@RequestBody标注的DishDto对象

    3.  DishDto实际包含Dish+DishFlavor，使用mybatis-plus不好添加。因此在servicxe中声明并实现方法：void saveDishWithFlavor(DishDto dishDto);

        1.  在向dish表中插入数据的同时，向dish flavor中插入数据

        2.  涉及多个表，应当添加事务控制

        [saveDsihWithFlavor](saveDsihWithFlavor/saveDsihWithFlavor.md "saveDsihWithFlavor")

### （2）分页+模糊查询

1.  参数：页码page、一页数据量pageSize、模糊查询关键字name（菜品名称）

2.  返回值：R.success(IPage<？？？>)

3.  实现方式：

    1.  确定返回值类型

        1.  Dish类属性：菜品id（dishId），前端显示的分类是分类名（Category中的属性）

        2.  由1，可以确定还需要对category进行查找，得到categoryName

        3.  注意，DishDto中有一属性即为categoryName。因此，返回值类型为IPage\<DishDto>

    2.  需要什么

        1.  IPage\<Dish>：查询菜品信息

        2.  IPage\<DishDto>：整合菜品信息与分类名

    3.  由上，封装业务方法“IPage\<DishDto> getDtoPage(IPage\<Dish> ipDish,String name);”，根据Dish Page与菜品名整合出DishDto Page

        [getDtoPage](getDtoPage/getDtoPage.md "getDtoPage")

    4.  整合中的注意事项

        1.  IPage\<Dish>→IPage\<DishDto>的拷贝需要BeanUtils.copyProperties方法，在拷贝的时候要忽略“records”属性，因为此时的records都是Dish

        2.  通过流的方式将IPage\<Dish>对象的records集合转化为Lsit\<DishDto>（同样需要拷贝），并将其赋给IPage\<DishDto>的records属性

### （3）修改菜品

#### （a）前端

[前端页面](前端页面/前端页面.md "前端页面")

#### （b）后端

1.  查询回显

    1.  根据id查询对应的菜品信息。最终返回的是R\<DishDto>

    2.  因此，创建业务方法“DishDto getOneFromDishAndFlavor(Long id);”：从Dish与Falvor中查询相应信息整合为DishDto

        [getOneFromDishAndFlavor](getOneFromDishAndFlavor/getOneFromDishAndFlavor.md "getOneFromDishAndFlavor")

2.  修改

    1.  创建业务方法“void updateOneToDishAndFlavor(DishDto dishDto);”：根据DishDto向Dish与Flavor中更新信息

        [updateOneToDishAndFlavor](updateOneToDishAndFlavor/updateOneToDishAndFlavor.md "updateOneToDishAndFlavor")

    2.  注意：

        1.  更新口味的时候可以采取措施：删除旧的添加新的

        2.  将新的口味添加到Flavor的时候，新的口味不会指明对应菜品的id，应该对每个口味都手动设定对应菜品的id

**删除菜品与修改菜品状态的约定：**

1.  **“起售”状态的菜品不能被删除**

2.  **套餐中的菜品一定是“起售”状态的**

3.  **停售菜品时，目标菜品不能包含在套餐中**

### （4）删除、批量删除菜品

见“第五天-删除、批量删除套餐”：（6）删除、批量删除套餐

### （5）停售、批量停售菜品

#### （a）前端

[前端页面](前端页面_1/前端页面.md "前端页面")

#### （b）后端

1.  参数

    1.  status。使用restful风格传参，添加注解@PathViriable("status")，并在@PostMapping中使用{status}包围

    2.  ids。使用List\<Long>类型，添加注解@RequestParam

2.  流程

    1.  ids是修改的目标集合 → in(...)

    2.  status是修改的属性 → set status = a\_status

    3.  status应该使用Dish类封装，通过自带的update方法进行修改

[DishController](DishController/DishController.md "DishController")

***

## 第五天

## 套餐管理

### （1）什么是套餐

主键id、分类id、名称价格...（不包含里面的菜品）

### （2）Dto类的设定

在前端页面中，分类显示的是分类名称，而且在对套餐进行操作的时候往往需要获取套餐中的菜品

### （3）新建套餐

#### （a）获取套餐分类

[前端页面](前端页面_1_2/前端页面.md "前端页面")

#### （b）向套餐中添加菜品

![](image/image_6Mmx-5XKso.png)

根据菜品分类的id查询对应集合（如上）

#### （c）保存新建的套餐

1.  保存→向套餐与套餐-菜品表中同时保存数据：创建业务方法：“void saveSetmealAndDish(SetmealDto setmealDto);”

2.  两张表→事务管理

3.  controller参数：@RequestBody SetmealDto→业务方法的参数也是SetmealDto类型

    1.  在set meal service中直接save这个dto对象→保存了基本的套餐信息

    2.  套餐-菜的保存：dto中有List\<SetmealDish>的属性，将其批量保存到set meal dish service即可

    3.  注意，2中缺少套餐id，应补齐

[saveSetmealAndDish](saveSetmealAndDish/saveSetmealAndDish.md "saveSetmealAndDish")

#### （d）controller中的操作

1.  调用保存方法

2.  返回success

### （4）模糊+分页查询

思路同菜品的“模糊+分页查询”：（2）分页+模糊查询

### （5）停售、批量停售套餐

见“第四天-菜品管理-停售、批量停售套餐”：（5）停售、批量停售菜品

### （6）删除、批量删除套餐

#### （a）前端页面分析

![](image/image_iR-WkZc-v9.png)

如上图所示，可以选择套餐然后点击批量删除（红色）或点击行尾的删除按钮（蓝色）进行（批量）删除

[前端页面](前端页面_1_2_3/前端页面.md "前端页面")

#### （b）后端分析

1.  返回值：R\<String>

2.  参数：由于批量删除与单个删除的url一样，也就是调用了同一个controller方法，因此将参数设定为List\<Long>

    1.  如果参数直接写List\<Long> ids会报500的错：No primary or default constructor found for interface java.util.List

    2.  在方法体内第一行添加断点后依旧报错，那么可以确定为传参的问题

    3.  传参方式：id1,id2,id3...→String\[]的方式，但是这里使用的是List：不匹配！

    4.  匹配方式：在参数前添加注解@RequestParam，或将参数改为String\[]，用逗号进行分割

3.  删除套餐的约定

    1.  只有套餐的status为0（禁售）才可以删除：根据ids中status==1的数目进行判定

    2.  删除关联信息+套餐信息两部分

        1.  简化qsl语句，使用in可以将多条sql语句化简为一条

        2.  批量删除

4.  创建业务方法“public void removeAfterJudge(List\<Long> ids)”。由于涉及到多表，添加事物控制注解

    [removeAfterJudge](removeAfterJudge/removeAfterJudge.md "removeAfterJudge")

### （7）修改套餐

见“第四天-菜品管理-修改菜品”：（3）修改菜品

[SetmealController](SetmealController/SetmealController.md "SetmealController")

***

## 第六天

## 前台用户登录功能

### （0）修改登录过滤器

[LoginFilter-new](LoginFilter-new/LoginFilter-new.md "LoginFilter-new")

### （1）用户的实体类

[User](User/User.md "User")

### （2）登录

#### （a）前端

[login.html](login.html/login.html.md "login.html")

#### （b）后端

1.  获取验证码

    1.  post方式以json对方的格式发送手机号到服务器端，使用@RequestBody标注的User对象作为方法参数

    2.  获取手机号，注意在后端同样要进行校验

        ```java
        String reg = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$";
        ```

    3.  生成随机验证码

        创建并调用ValidateCodeInitializer的相关静态方法即可

        [ValidateCodeInitializer](ValidateCodeInitializer/ValidateCodeInitializer.md "ValidateCodeInitializer")

    4.  调用api发送短信（不能真实使用，因为阿里云的审核严格）

        [SmsManager](SmsManager/SmsManager.md "SmsManager")

    5.  保存到session，以便后期登录时进行校验

        形式：手机号-验证码

    6.  返回R\<String>

2.  登录

    1.  User类中没有验证码这一属性，因此使用@RequestBody标注的UserDto扩展类对象作为参数，同时使用HttpSession从session作用域中获取验证码

        [UserDto](UserDto/UserDto.md "UserDto")

    2.  获取手机号与验证码，与session中的键值对进行比较，登录失败返回R.error

    3.  查数据库，存在→登录，不存在→添加后(自动注册)登录

    [UserController](UserController/UserController.md "UserController")

## 前台用户地址相关

### （1）地址的实体类

[AddressBook](AddressBook/AddressBook.md "AddressBook")

### （2）添加地址

#### （a）前端

[添加地址](添加地址/添加地址.md "添加地址")

#### （b）后端

1.  post是发送json对象，所以要使用@RequestBody标注的AddressBook对象

2.  传入的对象中的用户id不存在，因此先注入用户id

3.  保存并返回R\<AddressBook>

### （3）查看当前用户所有地址

#### （a）前端

[查看当前所有地址](查看当前所有地址/查看当前所有地址.md "查看当前所有地址")

#### （b）后端

1.  查询当前用户的所有地址

2.  返回R\<List\<AddressBook>>

### （4）添加默认地址

默认地址数量只能有一个

#### （a）前端

[默认地址](默认地址/默认地址.md "默认地址")

#### （b）后端

1.  put方法同样是发送json对象

2.  由于只能有一个默认地址，因此首先将所有地址的“默认”字段置为0

3.  将当前地址的“默认”字段置为1

4.  返回R\<String>

### （5）更新地址

#### （a）前端

[更新 = 查询+更新](<更新 = 查询+更新/更新 = 查询+更新.md> "更新 = 查询+更新")

#### （b）后端

1.  查询

    1.  通过restful的风格查询，传入的Long变量一定要使用@PathVariable注释进行注解

    2.  根据id在数据库中查询，失败返回R.error

2.  更新

    1.  put→json

    2.  根据id更新（id已经存在于json对象中，因此无需做别的操作）

### （6）删除地址

#### （a）前端

[删除一条地址](删除一条地址/删除一条地址.md "删除一条地址")

#### （b）后端

根据id删除

### （7）查询默认地址

#### （a）前端部分见“第七天-下单-前端”

#### （b）后端

1.  根据当前登录用户查询默认地址（is\_defult = 1）

2.  没有找到对象，返回R.error

3.  找到对象，由于此方法只有在下单时应用，因此可以在这里向session中添加一个键值对："orderTime" - LocalDateTime.now()，作为下单时间（不是付款时间）

[AddressBookController](AddressBookController/AddressBookController.md "AddressBookController")

## 前台用户购物车相关

### （1）购物车实体类

[entity——ShoppingCart](entity——ShoppingCart/entity——ShoppingCart.md "entity——ShoppingCart")

### （2）添加一个到购物车

#### （a）前端

[添加商品](添加商品/添加商品.md "添加商品")

在添加商品后，会调用controller的list方法异步刷新页面

#### （b）后端

1.  post→json

2.  未指定：userId、time，因此要补齐

3.  数据库操作：对于新商品→添加，对于已有商品→修改。因此在service中添加业务方法saveOrUpdateItem，并得到操作后的相应数据

    [业务方法saveOrUpdateItem](业务方法saveOrUpdateItem/业务方法saveOrUpdateItem.md "业务方法saveOrUpdateItem")

4.  返回R\<ShoppingCart>

### （3）减少一个数量

#### （a）前端

[减少商品](减少商品/减少商品.md "减少商品")

在添加商品后，会调用controller的list方法异步刷新页面

#### （b）后端

1.  post→json

2.  未指定：userId、time，因此要补齐

3.  数据库操作：对于number==1→添加，对于number>1→修改。因此在service中添加业务方法removeOrUpdateItem，并得到操作后的相应数据

    [业务方法removeOrUpdateItem](业务方法removeOrUpdateItem/业务方法removeOrUpdateItem.md "业务方法removeOrUpdateItem")

4.  返回R\<ShoppingCart>

### （4）查询当前用户的购物车信息

#### （a）前端

[所有购物车信息](所有购物车信息/所有购物车信息.md "所有购物车信息")

#### （b）后端

1.  根据当前用户id查询

2.  返回R\<List\<ShoppingCart>>

### （5）清空购物车

#### （a）前端

[清空购物车信息](清空购物车信息/清空购物车信息.md "清空购物车信息")

#### （b）后端

1.  根据当前用户id清空

2.  返回R\<String>

[ShoppingCartController](ShoppingCartController/ShoppingCartController.md "ShoppingCartController")

***

## 第七天

## 前台用户订单相关

### （1）实体类

[Orders](Orders/Orders.md "Orders")

[OrderDetail](OrderDetail/OrderDetail.md "OrderDetail")

### （2）下单

#### （a）前端

[下单的前端逻辑](下单的前端逻辑/下单的前端逻辑.md "下单的前端逻辑")

#### （b）后端

1.  post→json（object：Orders——传入的参数有remark、addrId、payMethod）

2.  向Orders对象置入当前用户id

3.  向Orders对象置入支付时间

4.  其余的参数整合在业务方法里面进行置入，因为还需要向Orders与OrderDetail表中插入数据

    [业务方法submitOrder](业务方法submitOrder/业务方法submitOrder.md "业务方法submitOrder")

[OrderController](OrderController/OrderController.md "OrderController")

***\[ 功能实现到此结束，后期会向里面继续添加功能，完善这个项目 ]***

***
